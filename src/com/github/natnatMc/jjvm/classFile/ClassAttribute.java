package com.github.natnatMc.jjvm.classFile;

import java.io.*;
import java.nio.*;

import com.github.natnatMc.jjvm.struct.CONSTANT_Utf8_info;

class ClassAttribute {
	
	public int len;
	public String name;
	public ByteBuffer data;
	
	public void read(DataInputStream in, ConstantPool pool) throws IOException {
		int pos=in.readUnsignedShort();
		CONSTANT_Utf8_info utf8=(CONSTANT_Utf8_info) pool.get(pos);
		name=utf8.value;
		len=in.readInt();
		data=ByteBuffer.allocate(len);
		in.readFully(data.array());
	}
	
}
