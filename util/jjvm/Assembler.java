package jjvm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.natnatMc.jjvm.classFile.ClassFile;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.interpreter.JClass;
import com.github.natnatMc.jjvm.interpreter.JOpCode;
import com.github.natnatMc.jjvm.source.BytecodeAssembler;

public class Assembler {
	
	public static void main(String... args) throws IOException, MalformedClassException {
		if(args.length<2) {
			printUsage();
			System.exit(1);
		}
		
		if(args.length>=3) {
			JOpCode.readFromFile(new File(args[2]));
		} else {
			JOpCode.readFromFile(new File("../data/opcodes.txt"));
		}
		
		File inFile=new File(args[0]);
		File outFile=new File(args[1]);
		
		BufferedReader read=new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
		JClass jClass=BytecodeAssembler.assemble(read, System.out);
		read.close();
		
		jClass.dump(System.out);
		
		ClassFile cFile=jClass.export();
		
		DataOutputStream dos=new DataOutputStream(new FileOutputStream(outFile));
		cFile.export(dos);
		dos.close();
	}
	
	public static void printUsage() {
		System.err.println("Usage: java jjvm.Assembler <asm> <cFile> [opCode]");
		System.err.println("\tWhere <asm> is the assembler class file");
		System.err.println("\t<cFile> is the output class file");
		System.err.println("\tand [opCode] is the opcode list file");
	}
	
}
