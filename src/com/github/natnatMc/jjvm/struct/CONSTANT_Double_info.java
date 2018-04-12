package com.github.natnatMc.jjvm.struct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_Double_info extends ConstantPoolObject {
	
	{
		this.type=6;
	}
	
	public double bytes;
	
	@Override
	public boolean equals(Object other) {
		if(other.getClass()!=this.getClass()) return false;
		CONSTANT_Double_info o=(CONSTANT_Double_info) other;
		return o.bytes==this.bytes;
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
		out.writeByte(type);
		out.writeDouble(bytes);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		bytes=in.readDouble();
	}
	
}
