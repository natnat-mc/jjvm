package com.github.natnatMc.jjvm.struct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_Fieldref_info extends ConstantPoolObject {
	
	{
		this.type=9;
	}
	
	public int classIndex;
	public int nameAndTypeIndex;
	
	@Override
	public boolean equals(Object other) {
		if(other.getClass()!=this.getClass()) return false;
		CONSTANT_Fieldref_info o=(CONSTANT_Fieldref_info) other;
		return o.classIndex==this.classIndex&&o.nameAndTypeIndex==this.nameAndTypeIndex;
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
		out.writeByte(type);
		out.writeChar(classIndex);
		out.writeChar(nameAndTypeIndex);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		classIndex=in.readUnsignedShort();
		nameAndTypeIndex=in.readUnsignedShort();
	}
	
}
