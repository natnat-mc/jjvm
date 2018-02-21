package com.github.natnatMc.jjvm.flags;

import java.util.*;

public class ClassFlags {
	
	public static final int ACC_PUBLIC=			0x0001;
	public static final int ACC_PRIVATE=		0x0002;
	public static final int ACC_PROTECTED=		0x0004;
	public static final int ACC_STATIC=			0x0008;
	public static final int ACC_FINAL=			0x0010;
	public static final int ACC_SUPER=			0x0020;
	public static final int ACC_SYNCHRONIZED=	0x0020;
	public static final int ACC_VOLATILE=		0x0040;
	public static final int ACC_TRANSIENT=		0x0080;
	public static final int ACC_NATIVE=			0x0100;
	public static final int ACC_INTERFACE=		0x0200;
	public static final int ACC_ABSTRACT=		0x0400;
	public static final int ACC_STRICT=			0x0800;
	
	public static String toStringClass(int flags) {
		ArrayList<String> list=new ArrayList<String>();
		if(isPublic(flags)) list.add("public");
		else if(isPrivate(flags)) list.add("private");
		else if(isProtected(flags)) list.add("protected");
		if(isStatic(flags)) list.add("static");
		if(isAbstract(flags)) list.add("abstract");
		else if(isFinal(flags)) list.add("final");
		if(isSuper(flags)) list.add("super");
		if(isVolatile(flags)) list.add("volatile");
		if(isTransient(flags)) list.add("transient");
		if(isInterface(flags)) list.add("interface");
		StringBuilder b=new StringBuilder();
		for(String flag:list) {
			if(b.length()!=0) b.append(' ');
			b.append(flag);
		}
		return b.toString();
	}
	public static String toStringMethod(int flags) {
		ArrayList<String> list=new ArrayList<String>();
		if(isPublic(flags)) list.add("public");
		else if(isPrivate(flags)) list.add("private");
		else if(isProtected(flags)) list.add("protected");
		if(isStatic(flags)) list.add("static");
		if(isAbstract(flags)) list.add("abstract");
		else if(isFinal(flags)) list.add("final");
		if(isSynchronized(flags)) list.add("synchronized");
		if(isNative(flags)) list.add("native");
		if(isStrict(flags)) list.add("strictfp");
		StringBuilder b=new StringBuilder();
		for(String flag:list) {
			if(b.length()!=0) b.append(' ');
			b.append(flag);
		}
		return b.toString();
	}
	public static String toStringField(int flags) {
		ArrayList<String> list=new ArrayList<String>();
		if(isPublic(flags)) list.add("public");
		else if(isPrivate(flags)) list.add("private");
		else if(isProtected(flags)) list.add("protected");
		if(isStatic(flags)) list.add("static");
		if(isFinal(flags)) list.add("final");
		if(isVolatile(flags)) list.add("volatile");
		if(isTransient(flags)) list.add("transient");
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
	public static boolean isPrivate(int val) {
		return hasFlags(val, ACC_PRIVATE);
	}
	public static boolean isProtected(int val) {
		return hasFlags(val, ACC_PROTECTED);
	}
	public static boolean isStatic(int val) {
		return hasFlags(val, ACC_STATIC);
	}
	public static boolean isFinal(int val) {
		return hasFlags(val, ACC_FINAL);
	}
	public static boolean isSuper(int val) {
		return hasFlags(val, ACC_SUPER);
	}
	public static boolean isSynchronized(int val) {
		return hasFlags(val, ACC_SYNCHRONIZED);
	}
	public static boolean isVolatile(int val) {
		return hasFlags(val, ACC_VOLATILE);
	}
	public static boolean isTransient(int val) {
		return hasFlags(val, ACC_TRANSIENT);
	}
	public static boolean isInterface(int val) {
		return hasFlags(val, ACC_INTERFACE);
	}
	public static boolean isAbstract(int val) {
		return hasFlags(val, ACC_ABSTRACT);
	}
	public static boolean isNative(int val) {
		return hasFlags(val, ACC_NATIVE);
	}
	public static boolean isStrict(int val) {
		return hasFlags(val, ACC_STRICT);
	}
	
}
