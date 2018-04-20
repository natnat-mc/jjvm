package com.github.natnatMc.jjvm.interpreter;

import java.nio.ByteBuffer;
import java.util.List;

public class JavaMethod {
	
	protected int modifier;
	protected ByteBuffer code;
	protected JavaExceptionHandler exceptionHandlers;
	protected int maxStack, maxLocals;
	protected List<JavaClass> params;
	protected JavaClass returnType;
	
}
