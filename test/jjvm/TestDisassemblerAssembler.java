package jjvm;

import java.io.File;
import java.io.IOException;

import com.github.natnatMc.jjvm.exceptions.MalformedClassException;

public class TestDisassemblerAssembler {
	
	public static void main(String[] args) throws IOException, MalformedClassException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		File asm1=TestHelper.getTestFile("SampleClass.asm");
		File package1=TestHelper.getTestDirectory("jjvm");
		File class1=new File(package1, "SampleClass.class");
		File asm2=TestHelper.getTestFile("SampleClass.asm2");
		
		Disassembler.main("bin/jjvm/SampleClass.class", asm1.getAbsolutePath(), "data/opcodes.txt");
		Assembler.main(asm1.getAbsolutePath(), class1.getAbsolutePath(), "data/opcodes.txt");
		
		Disassembler.main(class1.getAbsolutePath(), asm2.getAbsolutePath(), "data/opcodes.txt");
	}
	
}
