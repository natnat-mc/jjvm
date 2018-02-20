package com.github.natnatMc.jjvm.classFile;

import java.util.*;

public class ClassField {
	
	public int flags;
	public String name;
	public String descriptor;
	public ArrayList<ClassAttribute> attributes=new ArrayList<ClassAttribute>();
	
}
