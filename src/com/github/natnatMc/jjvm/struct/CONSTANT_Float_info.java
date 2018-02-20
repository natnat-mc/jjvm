package com.github.natnatMc.jjvm.struct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_Float_info extends ConstantPoolObject {
	
	{
		this.type=4;
	}
	
	public float bytes;
	
	@Override
	public boolean equals(Object other) {
		if(other.getClass()!=this.getClass()) return false;
		CONSTANT_Float_info o=(CONSTANT_Float_info) other;
		return o.bytes==this.bytes;
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
		out.writeByte(type);
		out.writeFloat(bytes);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		bytes=in.readFloat();
	}
	
}
