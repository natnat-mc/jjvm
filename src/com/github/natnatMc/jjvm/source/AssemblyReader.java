package com.github.natnatMc.jjvm.source;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.natnatMc.jjvm.classFile.*;
import com.github.natnatMc.jjvm.interpreter.*;
import com.github.natnatMc.jjvm.struct.ClassFlags;

public class AssemblyReader {
	
	private BufferedReader reader;
	private PrintStream status;
	
	private JClass generated=new JClass();
	private ConstantPool pool=new ConstantPool();
	private ArrayList<JField> fields=new ArrayList<JField>();
	private ArrayList<JMethod> methods=new ArrayList<JMethod>();
	
	private String name;
	
	public AssemblyReader(BufferedReader r) {
		reader=r;
	}

	//reads a class from disassembled bytecode
	public void read(PrintStream status) throws IOException {
		this.status=status;
		
		//class def line
		readClassLine(reader.readLine());
		
		String line;
		do {
			line=reader.readLine().trim();
			if(line==null) break;
			if(line.isEmpty()) continue;
			
			status.println("Reading line: "+line);
			
			if(line.contains("(")) {
				//we're reading a method
				status.println("Reading method");
				readMethod(line);
			} else if(line.contains(";")) {
				//we're reading a field
				readField(line);
			}
			
		} while(!line.trim().equals("}"));
	}
	
	//reads first line of class, containing modifier, superclass and class name
	private void readClassLine(String cLine) {
		//split get all parts of the line, delimited by whitespace
		String[] split=cLine.split("\\s+");
		
		//init the method
		int flags=0;
		int status=0;
		ArrayList<String> interfaces=new ArrayList<String>();
		
		//iterate through all words
		for(int i=0; i<split.length; i++) {
			String part=split[i];
			
			if(part.endsWith("{")) {
				//we're at the end of the line, and inside the class now
				part=part.substring(0, part.length()-1).trim();
				status=10;
			}
			
			//an empty part shouldn't happen, but it _can_ happen in some cases, like the last part
			if(part.isEmpty()) continue;
			
			if(status==0) {
				//reading all modifiers
				int val=ClassFlags.getFlag(part);
				if(part.equals("class")) {
					//no more modifiers after this point
					status++;
					generated.setFlags(flags);
					if(ClassFlags.isSuper(flags)) generated.setSuper("java.lang.Object");
				} else {
					flags|=val;
				}
			} else if(status==1) {
				//reading class name
				generated.setName(part);
				name=part;
				status++;
			} else if(status==2) {
				//extends or implements
				if(part.equals("extends")) status=3;
				else status=4;	//implements
			} else if(status==3) {
				//read superclass name
				generated.setSuper(part);
				status=2;
			} else if(status==4) {
				//read all interfaces
				if(part.equals(",")) continue;
				if(part.endsWith(",")) part=part.substring(0, part.length()-2);
				if(part.startsWith(",")) part=part.substring(1);
				interfaces.add(part);
			} else if(status>=10) {
				//we're done here
				break;
			}
		}
		//push all interfaces and constant pool
		generated.setInterfaces(interfaces);
		generated.setConstantPool(pool);
		
		//push the flags we just read
		generated.setFlags(flags);
	}
	
	//returns the generated JClass
	public JClass getGeneratedClass() {
		generated.setFields(fields);
		generated.setMethods(methods);
		return generated;
	}
	
	//reads a method
	private void readMethod(String line) throws IOException {
		int flags=0;
		String name=null;
		String type=null;
		
		//split by whitespace
		String[] split=line.split("\\s+");
		
		boolean pDone=false;
		int pos=0;
		ArrayList<String> shards=new ArrayList<String>();
		ArrayList<String> shards2=new ArrayList<String>();
		
		//read all modifiers
		for(; pos<split.length; pos++) {
			String mod=split[pos].trim();
			int flag=ClassFlags.getFlag(mod);
			
			if(flag!=0) {
				//we're really reading a flag, so it's all good
				flags|=flag;
			} else {
				//we're not reading a flag, so it's the type
				type=mod;
				break;
			}
		}
		
		//read name
		String mod=split[++pos].trim();
		if(mod.contains("(")) {
			//we're reading a parenthesis
			String[] parts=mod.split("\\(");
			name=parts[0];
			for(int i=1; i<parts.length; i++) {
				if(parts[i].equals(")")) {
					pDone=true;
				} else if(parts[i].contains(")")) {
					String[] pts=parts[i].split("\\)");
					pDone=true;
					shards.add(pts[0]);
					if(pts.length!=1) shards2.add(pts[1]);
				} else {
					shards.add(parts[i]);
				}
			}
			pos++;
		} else {
			name=mod;
		}
		
		if(!pDone) {
			//read parameters
			for(; pos<split.length; pos++) {
				mod=split[pos];
				if(mod.contains(")")) {
					String[] parts=mod.split("\\)");
					shards.add(parts[0]);
					if(parts.length!=1) shards2.add(parts[1]);
					pos++;
					break;
				} else {
					shards.add(mod);
				}
			}
		}
		
		//read end of line
		for(; pos<split.length; pos++) {
			mod=split[pos];
			shards2.add(mod);
		}
		
		//read params, throws and things like that
		StringBuilder paramsB=new StringBuilder();
		StringBuilder eolB=new StringBuilder();
		for(int i=0; i<shards.size(); i++) {
			if(i!=0) paramsB.append(' ');
			paramsB.append(shards.get(i));
		}
		for(int i=0; i<shards2.size(); i++) {
			if(i!=0) eolB.append(' ');
			eolB.append(shards2.get(i));
		}
		String params=paramsB.toString();
		String eol=eolB.toString();
		
		//debug logging, remove it when ok
		status.println("name is "+name);
		status.println("params is "+params);
		status.println("eol is "+eol);
		
		//list params
		List<String> paramList;
		if(params.length()==0) paramList=Collections.emptyList();
		else paramList=Arrays.asList(params.split(",\\s*"));
		
		List<String> exceptionList=new ArrayList<String>();
		Matcher throwsMatcher=PATTERN_THROWS.matcher(eol);
		if(throwsMatcher.find()) {
			String exceptionListStr=throwsMatcher.group(1);
			String[] exceptions=exceptionListStr.split(",\\s*");
			for(int i=0; i<exceptions.length; i++) {
				exceptionList.add(exceptions[i].trim());
			}
		}
		
		if(eol.endsWith(";")) {
			//there is no code to be seen here
			JMethod method=new JMethod();
			method.setName(name);
			method.setType(type);
			method.setFlags(flags);
			method.setParameters(paramList);
			methods.add(method);
		} else {
			//there is at least some code here
			JMethod assemble=assembleMethod(flags, name, type, paramList, exceptionList, reader, pool, status);
			methods.add(assemble);
		}
	}
	
	//reads a field
	private void readField(String line) {
		//strip the semicolon
		if(line.endsWith(";")) line=line.substring(0, line.length()-1);
		
		//split part in readable parts
		ArrayList<String> splits=new ArrayList<String>();
		{
			String[] split=line.split("\\s+");
			for(int i=0; i<split.length; i++) splits.add(split[i]);
		}
		
		//we initialize the reading
		int status=0;
		int flags=0;
		String type="";
		String name="";
		Object defValue=null;
		
		while(!splits.isEmpty()) {
			String part=splits.remove(0);
			if(status==0) {
				//reading modifiers
				int flag=ClassFlags.getFlag(part);
				if(flag!=0) {
					flags|=flag;
				} else {
					//we've got the type instead
					status++;
					type=part;
				}
			} else if(status==1) {
				//reading name and optionally value
				String[] split=part.split("=", 2);
				if(split.length==2) splits.add(0, split[1]);
				name=split[0];
				status++;
			} else if(status==2) {
				//reading value if present
				if(part.startsWith("=")) part=part.substring(1);
				if(!part.isEmpty()) {
					if(part.startsWith("\"")) {
						//we have a string
						while(!splits.isEmpty()) part+=" "+splits.remove(0);
						
						//decode the string
						StringBuilder valueBuilder=new StringBuilder();
						BytecodeAssembler.readString(valueBuilder, part);
						defValue=BytecodeAssembler.unescapeJava(valueBuilder.toString());
					} else {
						Matcher decimal=PATTERN_DECIMAL.matcher(part);
						if(decimal.find()) {
							//decimal value, double or float
							if(decimal.group(2)==null) {
								//double
								defValue=getDouble(decimal.group(1));
							} else {
								//float
								defValue=getFloat(decimal.group(1));
							}
						} else {
							Matcher integral=PATTERN_INTEGER.matcher(part);
							if(integral.find()) {
								//integral value, int or long
								if(integral.group(2)==null) {
									//int
									defValue=getInt(decimal.group(1));
								} else {
									//long
									defValue=getLong(decimal.group(1));
								}
							} else {
								//not a default value
							}
						}
					}
				}
			}
		}
		
		//actually return a field
		JField field=new JField();
		field.setFlags(flags);
		field.setName(name);
		field.setType(type);
		field.setConstantValue(defValue);
		fields.add(field);
	}
	
	public String getName() {
		return name;
	}
	
	//assembles a method
	public static JMethod assembleMethod(
											int flags,
											String name,
											String type,
											List<String> params,
											List<String> exceptions,
											BufferedReader reader,
											ConstantPool pool,
											PrintStream status)
													throws IOException {
		
		//initialize method read
		JMethod method=new JMethod();
		ArrayList<JExceptionHandler> exHandlers=new ArrayList<JExceptionHandler>();
		JCode code=new JCode();
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dout=new DataOutputStream(baos);
		
		boolean wide=false;
		
		String line=reader.readLine();
		while(line!=null) {
			line=line.trim();
			if(line.isEmpty()) continue;
			
			//if we're reading a closing bracket, it's time to stop
			if(line.equals("}")) break;
			
			//remove line numbers
			if(line.substring(0, 4).matches(STR_HEX_RAW)) line=line.substring(4).trim();
			
			//read exception handlers
			Matcher exHandler=PATTERN_EXCEPTION_HANDLER.matcher(line);
			if(exHandler.matches()) {
				//read base info
				JExceptionHandler handler=new JExceptionHandler();
				handler.startPC=getInt(exHandler.group(1));
				handler.endPC=getInt(exHandler.group(2));
				handler.handlerPC=getInt(exHandler.group(3));
				
				status.println("ExceptionHandler "+exHandler.groupCount());
				//we have a typed exception handler
				if(exHandler.groupCount()==4) handler.exceptionType=exHandler.group(4).replace('/', '.');
				
				//add handler to list
				exHandlers.add(handler);
				
				//onto next line
				line=reader.readLine();
				continue;
			}
			
			//read max stack and max locals
			Matcher maxStackOrLocals=PATTERN_MAX_STACK_OR_LOCAL.matcher(line);
			if(maxStackOrLocals.matches()) {
				//first group is the type
				String stackOrLocal=maxStackOrLocals.group(1);
				
				//second group is the value
				int val=getInt(maxStackOrLocals.group(2));
				
				//set the given value
				if(stackOrLocal.equals("Stack")) {
					code.maxStack=val;
				} else {
					code.maxLocals=val;
				}
				
				//onto next line
				line=reader.readLine();
				continue;
			}
			
			//read opcode or mnemonic
			String[] op=line.split("\\s+", 2);
			JOpCode opCode;
			if(op[0].matches(STR_HEX)) opCode=JOpCode.getByOpCode(getInt(op[0]));
			else opCode=JOpCode.getByMnemonic(op[0]);
			if(op.length!=1) line=op[1].trim();
			else line="";
			
			if(opCode==null) {
				status.println("Got null opCode: \""+op[0]+"\"");
			}
			
			//write opcode
			dout.writeByte(opCode.getOpCode());
			
			//handle wide opcode
			if(opCode.getOpCode()==OpCodes.wide) wide=true;
			
			//handle varArgs
			if(opCode.isVarArg()) {
				if(opCode.getOpCode()==OpCodes.lookupswitch) {
					status.println("Assembling lookupswitch");
					
					//read all info from line
					Matcher matcher=PATTERN_LOOKUPSWITCH.matcher(line);
					matcher.find();
					
					//zero-pad
					while(dout.size()%4!=0) dout.writeByte(0);
					
					//write default case
					dout.writeInt(getInt(matcher.group(1)));
					
					//read all cases
					matcher=PATTERN_LOOKUPSWITCH_ONE.matcher(matcher.group(2));
					ArrayList<String[]> matches=new ArrayList<String[]>();
					while(matcher.find()) {
						matches.add(new String[] {
							matcher.group(1),
							matcher.group(2)
						});
					}
					
					//write number of cases
					dout.writeInt(matches.size());
					
					//write all cases
					for(int i=0; i<matches.size(); i++) {
						dout.writeInt(getInt(matches.get(i)[0]));
						dout.writeInt(getInt(matches.get(i)[1]));
					}
				} else {
					status.println("Assembling tableswitch");
					
					//read all info from line
					Matcher matcher=PATTERN_TABLESWITCH.matcher(line);
					matcher.find();
					
					//zero-pad
					while(dout.size()%4!=0) dout.writeByte(0);
					
					//write default case, high and low values
					dout.writeInt(getInt(matcher.group(1)));
					dout.writeInt(getInt(matcher.group(2)));
					dout.writeInt(getInt(matcher.group(3)));
					
					//read and write all cases
					matcher=PATTERN_HEX.matcher(matcher.group(4));
					while(matcher.find()) {
						dout.writeInt(getInt(matcher.group(1)));
					}
				}
			} else {
				//read opCode arguments
				char[] args=wide?opCode.getWideArguments():opCode.getArguments();
				int firstArg=0;
				
				//read pool value and insert it
				if(opCode.isFromPool()) {
					firstArg++;
					int id=0;
					int end=0;
					status.println("Reading from pool");
					status.print("\tOpCode: ");
					status.println(opCode.getMnemonic());
					status.print("\tLine: ");
					status.println(line);
					switch(opCode.getOpCode()) {
						case OpCodes.ldc:
						case OpCodes.ldc_w:
						case OpCodes.ldc2_w:
							status.println("Assembling ldc-like opcode");
							//read a string, float, double, int or long
							if(line.startsWith("\"")) {
								//it's a string
								status.println("\ta string");
								StringBuilder str=new StringBuilder();
								end=BytecodeAssembler.readString(str, line);
								id=pool.requireString(BytecodeAssembler.unescapeJava(str.toString()));
								status.println("\t\""+str.toString()+"\"");
							} else {
								//it's a number
								Matcher matcher=PATTERN_DECIMAL.matcher(line);
								if(matcher.find()) {
									//it's a double or float
									status.println("\ta decimal");
									if(matcher.groupCount()==2&&matcher.group(2)!=null) {
										id=pool.requireFloat(getFloat(matcher.group(1)));
									} else {
										id=pool.requireDouble(getDouble(matcher.group(1)));
									}
								} else {
									//it's a long or int
									status.println("\tan integer");
									matcher=PATTERN_INTEGER.matcher(line);
									matcher.find();
									if(matcher.groupCount()==2&&matcher.group(2)!=null) {
										id=pool.requireLong(getLong(matcher.group(1)));
									} else {
										id=pool.requireInt(getInt(matcher.group(1)));
									}
								}
								end=matcher.end();
							}
							break;
						case OpCodes.new_:
						case OpCodes.anewarray:
						case OpCodes.multianewarray:
						case OpCodes.checkcast:
						case OpCodes.instanceof_:
							Matcher cName=PATTERN_JAVA_CLASS_INTERNAL.matcher(line);
							if(!cName.find()) {
								cName=PATTERN_JAVA_CLASS.matcher(line);
								cName.find();
							}
							end=cName.end();
							id=pool.requireClass(cName.group(1).replace(".", "/"));
							break;
						case OpCodes.getstatic:
						case OpCodes.putstatic:
						case OpCodes.getfield:
						case OpCodes.putfield:
							Matcher fieldRef=PATTERN_FIELDREF.matcher(line);
							fieldRef.find();
							end=fieldRef.end();
							id=pool.requireFieldref(fieldRef.group(1), fieldRef.group(2), fieldRef.group(3));
							break;
						case OpCodes.invokevirtual:
						case OpCodes.invokespecial:
						case OpCodes.invokestatic:
							fieldRef=PATTERN_FIELDREF.matcher(line);
							fieldRef.find();
							end=fieldRef.end();
							id=pool.requireMethodref(fieldRef.group(1), fieldRef.group(2), fieldRef.group(3));
							break;
						case OpCodes.invokeinterface:
							fieldRef=PATTERN_FIELDREF.matcher(line);
							fieldRef.find();
							end=fieldRef.end();
							id=pool.requireInterfaceMethodref(fieldRef.group(1), fieldRef.group(2), fieldRef.group(3));
							break;
						default:
							//TODO assemble other codes
							status.println("[UNIMPLEMENTED] "+opCode.getMnemonic());
					}
					if(args.length>1) {
						System.err.println(end+" "+line);
						if(line.length()>=end+1) line=line.substring(end+1).trim();
						else line="";
					}
					switch(args[0]) {
						case 'U':
							dout.writeByte(id&0xff);
							break;
						case 'C':
							dout.writeChar(id);
							break;
					}
				}
				
				for(int i=firstArg; i<args.length; i++) {
					if(args[i]=='0') {
						dout.writeByte(0);
						continue;
					}
					Matcher matcher=PATTERN_HEX.matcher(line);
					matcher.find();
					int arg=getInt(matcher.group(1));
					switch(args[i]) {
						case 'U':
						case 'B':
							dout.writeByte(arg&0xff);
							break;
						case 'C':
							dout.writeChar(arg);
							break;
						case 'S':
							dout.writeShort(arg);
							break;
						case 'I':
							dout.writeInt(arg);
							break;
						default:
							System.err.println(args[i]+" not handled");
							System.exit(1);
					}
				}
			}
			
			//onto next line
			line=reader.readLine();
			if(opCode.getOpCode()!=OpCodes.wide) wide=false;
		}
		
		//return full method
		code.code=ByteBuffer.wrap(baos.toByteArray());
		code.codeLen=code.code.capacity();
		code.exceptionTable=exHandlers.toArray(new JExceptionHandler[0]);
		code.exceptionTableLen=code.exceptionTable.length;
		method.setCode(code);
		method.setName(name);
		method.setFlags(flags);
		method.setParameters(params);
		method.setExceptions(exceptions);
		method.setType(type);
		return method;
	}
	
	//reads an int value from hex or decimal
	public static int getInt(String hexOrDec) {
		if(hexOrDec.startsWith("0x")) return Integer.parseInt(hexOrDec.substring(2), 16);
		if(hexOrDec.charAt(0)=='+'||hexOrDec.charAt(0)=='-') return Integer.parseInt(hexOrDec, 10);
		return Integer.decode(hexOrDec);
	}
	//reads a long value from hex or decimal
	public static long getLong(String hexOrDec) {
		if(hexOrDec.startsWith("0x")) return Long.parseLong(hexOrDec.substring(2), 16);
		if(hexOrDec.charAt(0)=='+'||hexOrDec.charAt(0)=='-') return Long.parseLong(hexOrDec, 10);
		return Long.decode(hexOrDec);
	}
	//reads a float value
	public static float getFloat(String str) {
		return Float.parseFloat(str);
	}
	//reads a double value
	public static double getDouble(String str) {
		return Double.parseDouble(str);
	}
	
	public static final String STR_HEX_RAW="([0-9a-fA-F]+)";
	public static final String STR_HEX="((?:0x)?[0-9a-fA-F]+)";
	public static final String STR_INTEGER="(-?(?:0x)?[0-9a-fA-F]+)(l|L)?";
	public static final String STR_DECIMAL="(-?(?:-|\\+)?[0-9]*\\.[0-9]+(?:[eE]-?[0-9]+)?)(f|F)?";
	public static final String STR_JAVA_IDENTIFIER="[a-zA-Z_][a-zA-Z_0-9]*";
	public static final String STR_JAVA_CLASS="("+STR_JAVA_IDENTIFIER+"(?:\\."+STR_JAVA_IDENTIFIER+")+)";
	public static final String STR_JAVA_CLASS_INTERNAL="("+STR_JAVA_IDENTIFIER+"(?:[/.]"+STR_JAVA_IDENTIFIER+")+)";
	public static final String STR_MAX_STACK_OR_LOCALS="^@Max(Stack|Locals)\\s+"+STR_HEX;
	public static final String STR_EXCEPTION_HANDLER_ALL="^@Exception handler\\s+"+STR_HEX+"\\s*-\\s*"+STR_HEX+"\\s+goto\\s+"+STR_HEX;
	public static final String STR_EXCEPTION_HANDLER=STR_EXCEPTION_HANDLER_ALL+"\\s*(?:\\s*type\\s+"+STR_JAVA_CLASS_INTERNAL+")?$";
	public static final String STR_LOOKUPSWITCH=STR_HEX+"\\s*(\\(.*\\))$";
	public static final String STR_LOOKUPSWITCH_ONE="\\(\\s*"+STR_HEX+"\\s*:\\s*"+STR_HEX+"\\s*\\)";
	public static final String STR_TABLESWITCH=STR_HEX+"\\s*"+STR_HEX+"\\s*"+STR_HEX+"\\s*(\\(.*\\))$";
	public static final String STR_FIELDREF=STR_JAVA_CLASS_INTERNAL+"\\s*:\\s*(<?"+STR_JAVA_IDENTIFIER+">?)\\s*:\\s*(\\S*)";
	public static final String STR_THROWS="\\s*throws\\s+([^{;]+)\\s*";

	public static final Pattern PATTERN_HEX_RAW=Pattern.compile(STR_HEX_RAW);
	public static final Pattern PATTERN_HEX=Pattern.compile(STR_HEX);
	public static final Pattern PATTERN_INTEGER=Pattern.compile(STR_INTEGER);
	public static final Pattern PATTERN_DECIMAL=Pattern.compile(STR_DECIMAL);
	public static final Pattern PATTERN_JAVA_IDENTIFIER=Pattern.compile("("+STR_JAVA_IDENTIFIER+")");
	public static final Pattern PATTERN_JAVA_CLASS=Pattern.compile(STR_JAVA_CLASS);
	public static final Pattern PATTERN_JAVA_CLASS_INTERNAL=Pattern.compile(STR_JAVA_CLASS_INTERNAL);
	public static final Pattern PATTERN_MAX_STACK_OR_LOCAL=Pattern.compile(STR_MAX_STACK_OR_LOCALS);
	public static final Pattern PATTERN_EXCEPTION_HANDLER_ALL=Pattern.compile(STR_EXCEPTION_HANDLER_ALL);
	public static final Pattern PATTERN_EXCEPTION_HANDLER=Pattern.compile(STR_EXCEPTION_HANDLER);
	public static final Pattern PATTERN_LOOKUPSWITCH=Pattern.compile(STR_LOOKUPSWITCH);
	public static final Pattern PATTERN_LOOKUPSWITCH_ONE=Pattern.compile(STR_LOOKUPSWITCH_ONE);
	public static final Pattern PATTERN_TABLESWITCH=Pattern.compile(STR_TABLESWITCH);
	public static final Pattern PATTERN_FIELDREF=Pattern.compile(STR_FIELDREF);
	public static final Pattern PATTERN_THROWS=Pattern.compile(STR_THROWS);
	
}
