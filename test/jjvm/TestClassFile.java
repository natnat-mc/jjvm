package jjvm;

import com.github.natnatMc.jjvm.classFile.ClassFile;
import java.io.*;

public class TestClassFile {
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
		ClassFile cFile=new ClassFile();
		FileInputStream in=new FileInputStream(new File("bin/jjvm/TestClassFile.class"));
		DataInputStream din=new DataInputStream(in);
		
		cFile.read(din);
		cFile.dump(System.out);
	}
	
}
