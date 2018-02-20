package com.github.natnatMc.jjvm.struct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_NameAndType_info extends ConstantPoolObject {
	
	{
		this.type=12;
	}
	
	public int nameIndex;
	public int descriptorIndex;
	
	@Override
	public boolean equals(Object other) {
		if(other.getClass()!=this.getClass()) return false;
		CONSTANT_NameAndType_info o=(CONSTANT_NameAndType_info) other;
		return o.nameIndex==this.nameIndex&&o.descriptorIndex==this.descriptorIndex;
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
		out.writeByte(type);
		out.writeChar(nameIndex);
		out.writeChar(descriptorIndex);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		nameIndex=in.readUnsignedShort();
		descriptorIndex=in.readUnsignedShort();
	}
	
}
