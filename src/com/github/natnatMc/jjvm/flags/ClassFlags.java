package com.github.natnatMc.jjvm.flags;

import java.util.*;

public class ClassFlags {
	
	public static final int ACC_PUBLIC=		0x0001;
	public static final int ACC_FINAL=		0x0010;
	public static final int ACC_SUPER=		0x0020;
	public static final int ACC_INTERFACE=	0x0200;
	public static final int ACC_ABSTRACT=	0x0400;
	
	public static String toString(int flags) {
		ArrayList<String> list=new ArrayList<String>();
		if(isPublic(flags)) list.add("public");
		if(isAbstract(flags)) list.add("abstract");
		if(isFinal(flags)) list.add("final");
		if(isSuper(flags)) list.add("super");
		if(isInterface(flags)) list.add("interface");
		StringBuilder b=new StringBuilder();
		for(String flag:list) {
			if(b.length()!=0) b.append(' ');
			b.append(flag);
		}
		return b.toString();
	}
	
	public static boolean hasFlags(int val, int flags) {
		return (val&flags)==flags;
	}
	
	public static boolean isPublic(int val) {
		return hasFlags(val, ACC_PUBLIC);
	}
	public static boolean isFinal(int val) {
		return hasFlags(val, ACC_FINAL);
	}
	public static boolean isSuper(int val) {
		return hasFlags(val, ACC_SUPER);
	}
	public static boolean isInterface(int val) {
		return hasFlags(val, ACC_INTERFACE);
	}
	public static boolean isAbstract(int val) {
		return hasFlags(val, ACC_ABSTRACT);
	}
	
}
