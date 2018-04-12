package com.github.natnatMc.jjvm.struct;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_NULL extends ConstantPoolObject {
	
	{
		this.type=-1;
	}
	
	public double bytes;
	
	@Override
	public boolean equals(Object other) {
		return false;
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
	}
	
}
