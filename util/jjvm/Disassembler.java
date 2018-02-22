package jjvm;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

import com.github.natnatMc.jjvm.classFile.*;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.flags.ClassFlags;
import com.github.natnatMc.jjvm.interpreter.*;
import com.github.natnatMc.jjvm.struct.*;
import com.github.natnatMc.jjvm.types.JObject;
import com.github.natnatMc.jjvm.types.JString;

public class Disassembler {
	
	public static void main(String[] args) throws IOException, MalformedClassException {
		if(args.length<2) {
			printUsage();
			System.exit(1);
		}
		
		//open class file
		File inFile=new File(args[0]);
		DataInputStream in=null;
		try {
			in=new DataInputStream(new FileInputStream(inFile));
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Can't read input file");
			System.exit(1);
		}
		if(!inFile.canRead()) {
			System.err.println("Can't read input file");
			System.exit(1);
		}
		
		//open output file
		File outFile=new File(args[1]);
		PrintStream out=null;
		try {
			out=new PrintStream(outFile);
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Can't write to output file");
			System.exit(1);
		}
		
		File opCodeFile=new File("../data/opcodes.txt");
		if(args.length==3) {
			opCodeFile=new File(args[2]);
		}
		try {
			JOpCode.readFromFile(opCodeFile);
		} catch(IOException e) {
			e.printStackTrace();
			System.err.println("Can't read opCode file");
			System.exit(1);
		}
		
		//read input class
		ClassFile cFile=new ClassFile();
		cFile.read(in);
		JClass jClass=new JClass(cFile);
		in.close();
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
			JObject cst=field.getConstantValue();
			if(cst!=null) {
				out.print('=');
				if(cst instanceof JString) {
					out.print('"');
					out.print(cst.toString());
					out.print('"');
				} else {
					out.print(cst.toString());
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
	
	public static void disassemble(JCode code, ConstantPool pool, PrintStream out, String indent, boolean printPos) {
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
				String posStr=Integer.toHexString(pos);
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
				out.print(" [vararg]");	//FIXME handle varargs
			}
			char[] args;
			if(wide) args=opcode.getWideArguments();
			else args=opcode.getArguments();
			
			if(args==null) {
				out.print(" [illegal call]");
				args=new char[0];
			}
			
			for(int i=0; i<args.length; i++) {
				out.print(' ');
				int val=0;
				switch(args[i]) {
					case 'U':
						val=buf.get(pos++)&0xff;
						break;
					case 'B':
						val=buf.get(pos++);
						break;
					case 'C':
						val=buf.getChar(pos++);
						break;
					case 'S':
						val=buf.getShort(pos++);
						break;
					case 'I':
						val=buf.getInt(pos++);
						break;
					case '0':
						val=buf.get(pos++);
						break;
				}
				if(i!=0||!opcode.isFromPool()) {
					out.print("0x");
					out.print(Integer.toHexString(val));
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
	
	private static String getPool(int index, ConstantPool pool) {
		try {
			ConstantPoolObject obj=pool.get(index);
			if(obj instanceof CONSTANT_Class_info) {
				int pos=((CONSTANT_Class_info) obj).pos;
				return ((CONSTANT_Utf8_info) pool.get(pos)).value;
			}
			if(obj instanceof CONSTANT_String_info) {
				int pos=((CONSTANT_String_info) obj).stringIndex;
				return '"'+((CONSTANT_Utf8_info) pool.get(pos)).value+'"';
			}
			return "["+obj.getClass().getSimpleName()+"]@"+index;
		} catch(IndexOutOfBoundsException e) {
			return "[ConstantPool value]@"+index;
		}
	}
	
	private static void printUsage() {
		System.err.println("Usage: java jjvm.Disassembler <input> <output>");
		System.err.println("\tWhere <input> is the input class");
		System.err.println("\tand <output> is the output file");
	}
	
}
