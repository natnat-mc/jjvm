package com.github.natnatMc.jjvm.struct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_Class_info extends ConstantPoolObject {
	
	{
		this.type=7;
	}
	
	public int pos;
	
	@Override
	public boolean equals(Object other) {
		if(other.getClass()!=this.getClass()) return false;
		CONSTANT_Class_info o=(CONSTANT_Class_info) other;
		return o.pos==this.pos;
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
		out.writeByte(type);
		out.writeChar(pos);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		pos=in.readUnsignedShort();
	}
	
}
