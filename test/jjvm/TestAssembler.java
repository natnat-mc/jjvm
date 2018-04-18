package jjvm;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;

import com.github.natnatMc.jjvm.classFile.*;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.intermediate.*;
import com.github.natnatMc.jjvm.source.*;

public class TestAssembler {
	
	public static void main(String[] args) throws MalformedClassException, IOException, IllegalArgumentException, IllegalAccessException {
		File inFile=new File("bin/com/github/natnatMc/jjvm/source/BytecodeAssembler.class");
		File outFile=TestHelper.getTestFile("BytecodeAssembler.asm");
		File outFile2=TestHelper.getTestFile("BytecodeAssembler.bin");
		File opCodeFile=new File("data/opcodes.txt");
		
		DataInputStream din=new DataInputStream(new FileInputStream(inFile));
		ClassFile cFile=new ClassFile();
		cFile.read(din);
		din.close();
		JClass jClass=new JClass(cFile);
		
		JOpCode.readFromFile(opCodeFile);
		
		PrintStream pst=new PrintStream(outFile);
		BytecodeAssembler.disassemble(jClass, pst, true);
		pst.close();
		
		BufferedReader read=new BufferedReader(new InputStreamReader(new FileInputStream(outFile)));
		while(!read.readLine().trim().equals("private static java.lang.String getPool(int, com.github.natnatMc.jjvm.classFile.ConstantPool) {"));
		
		ConstantPool pool=new ConstantPool();
		JMethod method=AssemblyReader.assembleMethod(0, "escapeJava", "java.lang.String", Arrays.asList("java.lang.String"), Collections.emptyList(), read, pool, System.err);
		read.close();
		
		byte[] bytes=method.getCode().code.array();
		FileOutputStream fos=new FileOutputStream(outFile2);
		fos.write(bytes);
		fos.close();
		
		pool.dump(System.out);
		
		BytecodeAssembler.disassemble(method.getCode(), pool, System.out, "\t\t", true);
	}
	
}
