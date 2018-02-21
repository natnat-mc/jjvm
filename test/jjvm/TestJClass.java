package jjvm;

import java.io.*;

import com.github.natnatMc.jjvm.classFile.*;
import com.github.natnatMc.jjvm.exceptions.MalformedClassException;
import com.github.natnatMc.jjvm.interpreter.JClass;

public class TestJClass implements Runnable {
	
	public static void main(String[] args) throws IOException, MalformedClassException {
		File testFile=new File("bin/jjvm/TestJClass.class");
		DataInputStream din=new DataInputStream(new FileInputStream(testFile));
		
		ClassFile cFile=new ClassFile();
		cFile.read(din);
		
		JClass jClass=new JClass(cFile);
		
		jClass.dump(System.out);
	}

	@Override
	public void run() {
		this.count+=INCREMENT;
		System.out.println(this.count);
	}
	
	public static void ttt() {
		TestJClass t=new TestJClass();
		t.run();
	}
	
	private int count=5;
	public static final int INCREMENT=1;
	
	static {
		ttt();
	}
	
}
