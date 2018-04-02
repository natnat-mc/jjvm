package jjvm;

import java.io.*;

import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.interpreter.JClass;
import com.github.natnatMc.jjvm.source.BytecodeAssembler;

public class TestFullAssembler {
	
	public static void main(String[] args) throws IOException, MalformedClassException {
		File outFile=TestHelper.getTestFile("ClassAttribute.asm");
		File inFile=new File("bin/com/github/natnatMc/jjvm/classFile/ClassAttribute.class");
		
		Disassembler.main(inFile.getAbsolutePath(), outFile.getAbsolutePath(), "data/opcodes.txt");
		
		BufferedReader read=new BufferedReader(new InputStreamReader(new FileInputStream(outFile)));
		JClass jClass=BytecodeAssembler.assemble(read, System.out);
		
		jClass.dump(System.err);
	}
	
}
