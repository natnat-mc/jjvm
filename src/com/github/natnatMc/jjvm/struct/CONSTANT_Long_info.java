package com.github.natnatMc.jjvm.struct;

import java.io.*;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_Long_info extends ConstantPoolObject {
	
	{
		this.type=5;
	}
	
	public long bytes;
	
	@Override
	public boolean equals(Object other) {
		if(other.getClass()!=this.getClass()) return false;
		CONSTANT_Long_info o=(CONSTANT_Long_info) other;
		return o.bytes==this.bytes;
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
		out.writeByte(type);
		out.writeLong(bytes);
		new CONSTANT_Integer_info().export(out);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		bytes=in.readLong();
	}
	
}
