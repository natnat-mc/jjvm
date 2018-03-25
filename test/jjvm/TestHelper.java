package jjvm;

import java.io.File;

public class TestHelper {
	
	public static final File root=new File("testFiles");
	
	public static File getTestFile(String name) {
		root.mkdirs();
		return new File(root, name);
	}
	public static File getTestDirectory(String name) {
		root.mkdirs();
		File dir=new File(root, name);
		dir.mkdirs();
		return dir;
	}
	
}
