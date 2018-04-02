package jjvm;

import java.io.*;

import com.github.natnatMc.jjvm.classFile.ClassFile;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.interpreter.JClass;
import com.github.natnatMc.jjvm.interpreter.JOpCode;
import com.github.natnatMc.jjvm.source.BytecodeAssembler;

public class TestDisassembler {
	
	public static void main(String[] args) throws IOException, MalformedClassException {
		File inFile=new File("bin/com/github/natnatMc/jjvm/source/AssemblyReader.class");
		File outFile=TestHelper.getTestFile("AssemblyReader.asm");
		File opCodeFile=new File("data/opcodes.txt");
		
		JOpCode.readFromFile(opCodeFile);
		
		DataInputStream din=new DataInputStream(new FileInputStream(inFile));
		PrintStream pst=new PrintStream(outFile);
		
		ClassFile cFile=new ClassFile();
		cFile.read(din);
		din.close();
		
		JClass jClass=new JClass(cFile);
		BytecodeAssembler.disassemble(jClass, pst, true);
		pst.close();
	}
	
}
