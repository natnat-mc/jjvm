package jjvm;

import java.io.*;

import com.github.natnatMc.jjvm.interpreter.*;

public class Assembler {
	
	public static void main(String... args) {
		if(args.length<2) {
			printUsage();
			System.exit(1);
		}
	}
	
	public static void printUsage() {
		System.err.println("Usage: java jjvm.Assembler <asm> <cFile> [opCode]");
		System.err.println("\tWhere <asm> is the assembler class file");
		System.err.println("\t<cFile> is the output class file");
		System.err.println("\tand [opCode] is the opcode list file");
	}
	
}
