package jjvm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import com.github.natnatMc.jjvm.exceptions.MalformedClassException;

public class TestDisassemblerAssembler {
	
	public static void main(String[] args) throws IOException, MalformedClassException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		File asm1=TestHelper.getTestFile("SampleClass.asm");
		File package1=TestHelper.getTestDirectory("jjvm");
		File class1=new File(package1, "SampleClass.class");
		File asm2=TestHelper.getTestFile("SampleClass.asm2");
		
		Disassembler.main("bin/jjvm/SampleClass.class", asm1.getAbsolutePath(), "data/opcodes.txt");
		Assembler.main(asm1.getAbsolutePath(), class1.getAbsolutePath(), "data/opcodes.txt");
		
		URLClassLoader loader=new URLClassLoader(new URL[] {class1.getParentFile().toURI().toURL()});
		Class<?> clazz=loader.loadClass("jjvm.SampleClass");
		Runnable instance=(Runnable) clazz.newInstance();
		instance.run();
		loader.close();
		
		Disassembler.main(class1.getAbsolutePath(), asm2.getAbsolutePath(), "data/opcodes.txt");
	}
	
}
