package com.github.natnatMc.jjvm.source;

import java.io.*;
import java.nio.*;
import java.util.*;
import com.github.natnatMc.jjvm.classFile.*;
import com.github.natnatMc.jjvm.interpreter.*;
import com.github.natnatMc.jjvm.struct.*;

public class BytecodeAssembler {
	
	//escape a java String
	public static String escapeJava(String unescaped) {
		StringBuilder builder=new StringBuilder();
		int len=unescaped.length();
		for(int i=0; i<len; i++) {
			char chr=unescaped.charAt(i);
			switch(chr) {
				case '\\':
					builder.append("\\\\");
					break;
				case '\n':
					builder.append("\\n");
					break;
				case '\t':
					builder.append("\\t");
					break;
				case '\b':
					builder.append("\\b");
					break;
				case '\f':
					builder.append("\\f");
					break;
				case '\r':
					builder.append("\\r");
					break;
				case '\"':
					builder.append("\\\"");
					break;
				case '\'':
					builder.append("\\\'");
					break;
				default:
					builder.append(chr);
			}
		}
		return builder.toString();
	}
	
	//unescape a previously escaped java String
	public static String unescapeJava(String escaped) {
		StringBuilder builder=new StringBuilder();
		boolean backslash=false;
		int len=escaped.length();
		char chr=0;
		char lastChr=0;
		for(int i=0; i<len; i++) {
			lastChr=chr;
			chr=escaped.charAt(i);
			if(lastChr=='\\') backslash=!backslash;
			else backslash=false;
			if(lastChr=='\\'&&backslash) builder.deleteCharAt(builder.length()-1);
			if(!backslash) {
				builder.append(chr);
			} else {
				switch(chr) {
					case 'n':
						builder.append('\n');
						break;
					case 't':
						builder.append('\t');
						break;
					case 'b':
						builder.append('\b');
						break;
					case 'f':
						builder.append('\f');
						break;
					case 'r':
						builder.append('\r');
						break;
					case '\"':
						builder.append('\"');
						break;
					case '\'':
						builder.append('\'');
						break;
					case '\\':
						builder.append('\\');
						break;
				}
			}
		}
		return builder.toString();
	}
	
	//read a java String
	public static int readString(StringBuilder str, String line) {
		boolean escape=false;
		int pos=1;
		int chr=line.charAt(pos);
		int len=line.length();
		for(pos++; pos<len&&!(chr=='"'&&!escape); pos++) {
			if(chr=='\\') escape=!escape;
			str.append((char) chr);
			chr=line.charAt(pos);
		}
		return pos;
	}
	
	//disassemble a class
	public static void disassemble(JClass jClass, PrintStream out, boolean printPos) {
		ConstantPool pool=jClass.getConstantPool();
		
		//write disassembled class header
				String flags=ClassFlags.toStringClass(jClass.getFlags());
				if(!flags.isEmpty()) {
					out.print(flags);
					out.print(' ');
				}
				out.print("class ");
				out.print(jClass.getName());
				if(jClass.getSuper()!=null) {
					out.print(" extends ");
					out.print(jClass.getSuper());
				}
				List<String> ifaces=jClass.getInterfaces();
				for(int i=0; i<ifaces.size(); i++) {
					if(i==0) out.print(" implements ");
					else out.print(", ");
					out.print(ifaces.get(i));
				}
				out.println(" {");
				out.println('\t');
				
				//list all class fields
				List<JField> fields=jClass.getFields();
				for(int i=0; i<fields.size(); i++) {
					out.print('\t');
					JField field=fields.get(i);
					flags=ClassFlags.toStringField(field.getFlags());
					if(!flags.isEmpty()) {
						out.print(flags);
						out.print(' ');
					}
					out.print(field.getType());
					out.print(' ');
					out.print(field.getName());
					Object cst=field.getConstantValue();
					if(cst!=null) {
						out.print('=');
						if(cst instanceof String) {
							out.print('"');
							out.print(cst.toString());
							out.print('"');
						} else {
							out.print(cst.toString());
							if(cst instanceof Float) out.print('f');
							else if(cst instanceof Long) out.print('L');
						}
					}
					out.println(";");
				}
				if(!fields.isEmpty()) out.println('\t');
				
				//list all methods
				List<JMethod> methods=jClass.getMethods();
				for(int i=0; i<methods.size(); i++) {
					out.print('\t');
					JMethod method=methods.get(i);
					flags=ClassFlags.toStringMethod(method.getFlags());
					if(!flags.isEmpty()) {
						out.print(flags);
						out.print(' ');
					}
					out.print(method.getType());
					out.print(' ');
					out.print(method.getName());
					out.print('(');
					List<String> params=method.getParameters();
					for(int j=0; j<params.size(); j++) {
						if(j!=0) out.print(", ");
						out.print(params.get(j));
					}
					out.print(')');
					List<String> exceptions=method.getExceptions();
					for(int j=0; j<exceptions.size(); j++) {
						if(j==0) out.print(" throws ");
						else out.print(", ");
						out.print(exceptions.get(j));
					}
					JCode code=method.getCode();
					if(code!=null&&!ClassFlags.isAbstract(method.getFlags())) {
						out.println(" {");
						try {
							disassemble(code, pool, out, "\t\t", true);
						} catch(Exception e) {
							e.printStackTrace();
							out.println("[disassembler error]");
						}
						out.println("\t}");
					} else {
						out.println(';');
					}
				}
				out.println('\t');
				
				//write disassembled class footer
				out.println('}');
	}
	
	//disassemble a method
	public static void disassemble(JCode code, ConstantPool pool, PrintStream out, String indent, boolean printPos) {
		out.print(indent);
		out.print("@MaxStack 0x");
		out.println(Integer.toHexString(code.maxStack));
		out.print(indent);
		out.print("@MaxLocals 0x");
		out.println(Integer.toHexString(code.maxLocals));
		for (JExceptionHandler handler:code.exceptionTable) {
			out.print(indent);
			out.print("@Exception handler 0x");
			out.print(Integer.toHexString(handler.startPC));
			out.print("-0x");
			out.print(Integer.toHexString(handler.endPC));
			out.print(" goto 0x");
			out.print(Integer.toHexString(handler.handlerPC));
			if(handler.exceptionType!=null) {
				out.print(" type ");
				out.print(handler.exceptionType);
			}
			out.println();
		}
		int pos=0;
		boolean wide=false;
		ByteBuffer buf=code.code;
		while(pos<code.codeLen) {
			//read opcode
			int op=buf.get(pos++)&0xff;
			JOpCode opcode=JOpCode.getByOpCode(op);
			
			//indent asm
			out.print(indent);
			
			if(printPos) {
				String posStr=Integer.toHexString(pos-1);
				while(posStr.length()<4) posStr="0"+posStr;
				out.print(posStr);
				out.print(' ');
			}
			
			//handle unrecognized opcodes
			if(opcode==null) {
				out.print("0x");
				if(op<0x10) out.print('0');
				out.println(Integer.toHexString(op));
				wide=false;
				continue;
			}
			
			//print opcode mnemonic
			out.print(opcode.getMnemonic());
			
			//handle wide opcode
			if(op==OpCodes.wide) wide=true;
			
			if(opcode.isVarArg()) {
				if(op==OpCodes.lookupswitch) {
					pos+=(4-pos%4)%4;
					out.print(" 0x");
					out.print(Integer.toHexString(buf.getInt(pos)));
					pos+=4;
					int len=buf.getInt(pos);
					pos+=4;
					for(int i=0; i<len; i++) {
						out.print(" (0x");
						out.print(Integer.toHexString(buf.getInt(pos)));
						pos+=4;
						out.print(": 0x");
						out.print(Integer.toHexString(buf.getInt(pos)));
						pos+=4;
						out.print(")");
					}
				} else if(op==OpCodes.tableswitch) {
					pos+=(4-pos%4)%4;
					out.print(" 0x");
					out.print(Integer.toHexString(buf.getInt(pos)));
					pos+=4;
					int low=buf.getInt(pos);
					pos+=4;
					int high=buf.getInt(pos);
					pos+=4;
					out.print(" 0x");
					out.print(Integer.toHexString(low));
					out.print(" 0x");
					out.print(Integer.toHexString(high));
					out.print(" (");
					int len=high-low+1;
					for(int i=0; i<len; i++) {
						out.print("0x");
						out.print(Integer.toHexString(buf.getInt(pos)));
						pos+=4;
					}
					out.print(")");
				} else {
					out.print(" [VarArg]");
				}
			}
			char[] args;
			if(wide) args=opcode.getWideArguments();
			else args=opcode.getArguments();
			
			if(args==null) {
				out.print(" [illegal call]");
				args=new char[0];
			}
			
			for(int i=0; i<args.length; i++) {
				int val=0;
				switch(args[i]) {
					case 'U':
						val=buf.get(pos++)&0xff;
						break;
					case 'B':
						val=buf.get(pos++);
						break;
					case 'C':
						val=buf.getChar(pos);
						pos+=2;
						break;
					case 'S':
						val=buf.getShort(pos);
						pos+=2;
						break;
					case 'I':
						val=buf.getInt(pos);
						pos+=4;
						break;
					case '0':
						val=buf.get(pos++);
						continue;
				}
				out.print(' ');
				if(i!=0||!opcode.isFromPool()) {
					if(val>0) {
						out.print("0x");
						out.print(Integer.toHexString(val));
					} else {
						out.print(val);
					}
				} else {
					out.print(getPool(val, pool));
				}
			}
			
			//stop reading as wide when the time comes
			if(op!=OpCodes.wide) wide=false;
			
			//end line
			out.println();
		}
	}
	
	//get a value from the constant pool, in textual form
	private static String getPool(int index, ConstantPool pool) {
		try {
			ConstantPoolObject obj=pool.get(index);
			if(obj instanceof CONSTANT_Class_info) {
				int pos=((CONSTANT_Class_info) obj).pos;
				return ((CONSTANT_Utf8_info) pool.get(pos)).value;
			} else if(obj instanceof CONSTANT_String_info) {
				int pos=((CONSTANT_String_info) obj).stringIndex;
				return '"'+escapeJava(((CONSTANT_Utf8_info) pool.get(pos)).value)+'"';
			} else if(obj instanceof CONSTANT_Methodref_info) {
				CONSTANT_Methodref_info info=(CONSTANT_Methodref_info) obj;
				CONSTANT_Class_info cRef=(CONSTANT_Class_info) pool.get(info.classIndex);
				CONSTANT_Utf8_info cName=(CONSTANT_Utf8_info) pool.get(cRef.pos);
				CONSTANT_NameAndType_info ntRef=(CONSTANT_NameAndType_info) pool.get(info.nameAndTypeIndex);
				CONSTANT_Utf8_info nRef=(CONSTANT_Utf8_info) pool.get(ntRef.nameIndex);
				CONSTANT_Utf8_info tRef=(CONSTANT_Utf8_info) pool.get(ntRef.descriptorIndex);
				return cName.value+":"+nRef.value+":"+tRef.value;
			} else if(obj instanceof CONSTANT_InterfaceMethodref_info) {
				CONSTANT_InterfaceMethodref_info info=(CONSTANT_InterfaceMethodref_info) obj;
				CONSTANT_Class_info cRef=(CONSTANT_Class_info) pool.get(info.classIndex);
				CONSTANT_Utf8_info cName=(CONSTANT_Utf8_info) pool.get(cRef.pos);
				CONSTANT_NameAndType_info ntRef=(CONSTANT_NameAndType_info) pool.get(info.nameAndTypeIndex);
				CONSTANT_Utf8_info nRef=(CONSTANT_Utf8_info) pool.get(ntRef.nameIndex);
				CONSTANT_Utf8_info tRef=(CONSTANT_Utf8_info) pool.get(ntRef.descriptorIndex);
				return cName.value+":"+nRef.value+":"+tRef.value;
			} else if(obj instanceof CONSTANT_Fieldref_info) {
				CONSTANT_Fieldref_info info=(CONSTANT_Fieldref_info) obj;
				CONSTANT_Class_info cRef=(CONSTANT_Class_info) pool.get(info.classIndex);
				CONSTANT_Utf8_info cName=(CONSTANT_Utf8_info) pool.get(cRef.pos);
				CONSTANT_NameAndType_info ntRef=(CONSTANT_NameAndType_info) pool.get(info.nameAndTypeIndex);
				CONSTANT_Utf8_info nRef=(CONSTANT_Utf8_info) pool.get(ntRef.nameIndex);
				CONSTANT_Utf8_info tRef=(CONSTANT_Utf8_info) pool.get(ntRef.descriptorIndex);
				return cName.value+":"+nRef.value+":"+tRef.value;
			} else if(obj instanceof CONSTANT_Integer_info) {
				return ((CONSTANT_Integer_info) obj).bytes+"";
			} else if(obj instanceof CONSTANT_Long_info) {
				return ((CONSTANT_Long_info) obj).bytes+"L";
			} else if(obj instanceof CONSTANT_Float_info) {
				return ((CONSTANT_Float_info) obj).bytes+"f";
			} else if(obj instanceof CONSTANT_Double_info) {
				return ((CONSTANT_Double_info) obj).bytes+"";
			}
			return "["+obj.getClass().getSimpleName()+"]@"+index;
		} catch(IndexOutOfBoundsException e) {
			return "[ConstantPool value]@"+index;
		}
	}
	
	//assemble a class
	public static JClass assemble(BufferedReader in, PrintStream status) throws IOException {
		AssemblyReader reader=new AssemblyReader(in);
		reader.read(status);
		return reader.getGeneratedClass();
	}
}
