package com.github.natnatMc.jjvm.struct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_Integer_info extends ConstantPoolObject {
	
	{
		this.type=3;
	}
	
	public int bytes;
	
	@Override
	public boolean equals(Object other) {
		if(other.getClass()!=this.getClass()) return false;
		CONSTANT_Integer_info o=(CONSTANT_Integer_info) other;
		return o.bytes==this.bytes;
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
		out.writeByte(type);
		out.writeInt(bytes);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		bytes=in.readInt();
	}
	
}
