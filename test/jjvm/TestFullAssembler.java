package jjvm;

import java.io.*;

import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.intermediate.JClass;
import com.github.natnatMc.jjvm.source.BytecodeAssembler;

public class TestFullAssembler {
	
	public static void main(String[] args) throws IOException, MalformedClassException {
		File out1=TestHelper.getTestFile("ClassAttribute.asm");
		File in1=new File("bin/com/github/natnatMc/jjvm/classFile/ClassAttribute.class");
		test(in1, out1);
		
		File out2=TestHelper.getTestFile("AssemblyReader.asm");
		File in2=new File("bin/com/github/natnatMc/jjvm/source/AssemblyReader.class");
		test(in2, out2);
	}
	
	public static void test(File in, File out) throws IOException, MalformedClassException {
		Disassembler.main(in.getAbsolutePath(), out.getAbsolutePath(), "data/opcodes.txt");
		BufferedReader read=new BufferedReader(new InputStreamReader(new FileInputStream(out)));
		JClass jClass=BytecodeAssembler.assemble(read, System.out);
		
		jClass.dump(System.err);
	}
	
}
