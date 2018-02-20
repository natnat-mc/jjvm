package jjvm;

import com.github.natnatMc.jjvm.classFile.ConstantPool;
import java.io.*;

public class TestConstantPool {
	
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
		ConstantPool pool=new ConstantPool();
		FileInputStream in=new FileInputStream(new File("bin/jjvm/TestConstantPool.class"));
		DataInputStream din=new DataInputStream(in);
		
		din.skipBytes(8);
		
		pool.read(din);
		pool.dump(System.out);
	}
	
}
