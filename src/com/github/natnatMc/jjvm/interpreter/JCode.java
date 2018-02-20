package com.github.natnatMc.jjvm.interpreter;

import java.nio.*;
import com.github.natnatMc.jjvm.classFile.*;
import com.github.natnatMc.jjvm.struct.CONSTANT_Class_info;
import com.github.natnatMc.jjvm.struct.CONSTANT_Utf8_info;

public class JCode {
	
	public int codeLen;
	public ByteBuffer code;
	
	public int maxStack;
	public int maxLocals;
	
	public int exceptionTableLen;
	public JExceptionHandler[] exceptionTable;
	
	public void read(ClassAttribute attr, ConstantPool pool) {
		attr.data.position(0);
		
		//read max stack and locals
		maxStack=attr.data.getChar();
		maxLocals=attr.data.getChar();
		
		//read the actual code
		codeLen=attr.data.getInt();
		code=ByteBuffer.allocate(codeLen);
		attr.data.get(code.array());
		
		//read exception tables (try/catch/finally)
		exceptionTableLen=attr.data.getChar();
		exceptionTable=new JExceptionHandler[exceptionTableLen];
		for(int i=0; i<exceptionTableLen; i++) {
			//read general info
			JExceptionHandler handler=new JExceptionHandler();
			handler.startPC=attr.data.getChar();
			handler.endPC=attr.data.getChar();
			handler.handlerPC=attr.data.getChar();
			
			//if this is a catch or a finally
			int pos=attr.data.getChar();
			if(pos!=0) {
				CONSTANT_Class_info info=(CONSTANT_Class_info) pool.get(pos);
				CONSTANT_Utf8_info utf8=(CONSTANT_Utf8_info) pool.get(info.pos);
				handler.exceptionType=utf8.value.replaceAll("/", ".");
			} else {
				handler.exceptionType="*";
			}
			
			//add this to the list
			exceptionTable[i]=handler;
		}
		
		//don't read any attribute, as we don't *need* them
	}
	
}
