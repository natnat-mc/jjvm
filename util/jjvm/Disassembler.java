package jjvm;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

import com.github.natnatMc.jjvm.classFile.*;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.flags.ClassFlags;
import com.github.natnatMc.jjvm.interpreter.*;
import com.github.natnatMc.jjvm.source.BytecodeAssembler;
import com.github.natnatMc.jjvm.struct.*;
import com.github.natnatMc.jjvm.types.JObject;
import com.github.natnatMc.jjvm.types.JString;

public class Disassembler {
	
	public static void main(String... args) throws IOException, MalformedClassException {
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
		
		BytecodeAssembler.disassemble(jClass, out, true);
	}
	
	private static void printUsage() {
		System.err.println("Usage: java jjvm.Disassembler <input> <output> [opCode]");
		System.err.println("\tWhere <input> is the input class");
		System.err.println("\t<output> is the output file");
		System.err.println("\tand [opCode] is the opCode list file");
	}
	
}
