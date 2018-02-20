package com.github.natnatMc.jjvm.struct;

import java.io.*;
import java.nio.ByteBuffer;

import com.github.natnatMc.jjvm.classFile.ConstantPoolObject;

public class CONSTANT_Utf8_info extends ConstantPoolObject {
	
	{
		this.type=1;
	}
	
	public String value="";
	
	@Override
	public boolean equals(Object other) {
		if(other.getClass()!=this.getClass()) return false;
		CONSTANT_Utf8_info o=(CONSTANT_Utf8_info) other;
		return o.value.equals(this.value);
	}

	@Override
	public void export(DataOutputStream out) throws IOException {
		out.writeByte(type);
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		DataOutputStream dout=new DataOutputStream(os);
		for(char chr:value.toCharArray()) {
			if(chr>0&&chr<0x80) {
				dout.writeByte(chr);
			} else if(chr<0x800) {
				int upper=(chr>>6)&0x1f;
				int lower=chr&0x3f;
				dout.writeByte(0xc0|upper);
				dout.writeByte(0x80|lower);
			} else { //if(chr<0xffff)
				int x=(chr>>12)&0xf;
				int y=(chr>>6)&0x1f;
				int z=chr&0x3f;
				dout.writeByte(0xe0|x);
				dout.writeByte(0xc0|y);
				dout.writeByte(0x80|z);
			}
		}
		dout.flush();
		dout.close();
		byte[] arr=os.toByteArray();
		out.writeChar(arr.length);
		out.write(arr);
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		int len=in.readUnsignedShort();
		ByteBuffer buf=ByteBuffer.allocate(len);
		in.readFully(buf.array());
		StringBuilder b=new StringBuilder();
		while(buf.remaining()!=0) {
			char c=0;
			int x=buf.get()&0xff;
			if(x<0x80) {
				b.append((char) x);
			} else {
				int y=buf.get()&0xff;
				if((x&0xe0)==0xe0) {
					int z=buf.get()&0xff;
					c=(char) (((x&0xf)<<12)+((y&0x3f)<<6)+(z&0x3f));
				} else {
					c=(char) (((x&0x1f)<<6)+(y&0x3f));
				}
				b.append(c);
			}
		}
		value=b.toString();
	}
	
}
