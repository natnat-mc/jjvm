package jjvm;

import com.github.natnatMc.jjvm.classFile.ClassFile;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

public class TestClassFile implements Runnable {
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		File outFile=TestHelper.getTestFile("TestClassFile.class");
		
		ClassFile cFile=new ClassFile();
		FileInputStream in=new FileInputStream(new File("bin/jjvm/TestClassFile.class"));
		DataInputStream din=new DataInputStream(in);
		
		cFile.read(din);
		cFile.dump(System.out);
		din.close();
		
		FileOutputStream out=new FileOutputStream(outFile);
		DataOutputStream dout=new DataOutputStream(out);
		new File("test/TestClassFile.class").deleteOnExit();
		
		cFile.export(dout);
		dout.close();
		cFile=new ClassFile();
		
		in=new FileInputStream(outFile);
		din=new DataInputStream(in);
		
		cFile.read(din);
		cFile.dump(System.err);
		din.close();
		
		new ClassLoader(ClassLoader.getSystemClassLoader()) {
			{
				byte[] b=Files.readAllBytes(outFile.toPath());
				Class<?> c=defineClass("jjvm.TestClassFile", b, 0, b.length);
				Class<?> m=TestClassFile.class;
				if(!c.getName().equals(m.getName())) throw new RuntimeException("Wrong name");
				c.getDeclaredMethod("ttt").invoke(null);
				
				System.out.println("If written 6 abobe, it is all good!");
			}
		};
	}
	
	public static void ttt() {
		new TestClassFile().run();
	}
	
	public void run() {
		this.count+=INCREMENT;
		System.out.println(this.count);
	}
	
	private int count=5;
	private static final int INCREMENT=1;
	
}
