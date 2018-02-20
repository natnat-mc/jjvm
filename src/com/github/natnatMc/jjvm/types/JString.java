package com.github.natnatMc.jjvm.types;

public class JString extends JObject {
	
	private String value;
	
	public JString(String s) {
		this.value=s;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	@Override
	public boolean isNumber() {
		return true;
	}
	
	@Override
	public JDecimal toDecimal() {
		return new JDecimal(Double.parseDouble(value));
	}
	@Override
	public JInteger toInteger() {
		return new JInteger(Long.decode(value));
	}
	@Override
	public long toLong() {
		return toDecimal().toLong();
	}
	@Override
	public double toDouble() {
		return toInteger().toDouble();
	}
	@Override
	public int toInt() {
		return toInteger().toInt();
	}
	@Override
	public float toFloat() {
		return toDecimal().toFloat();
	}
	@Override
	public char toChar() {
		return toInteger().toChar();
	}
	@Override
	public short toShort() {
		return toInteger().toShort();
	}
	@Override
	public boolean toBoolean() {
		return toInteger().toBoolean();
	}
	@Override
	public byte toByte() {
		return toInteger().toByte();
	}
	
	@Override
	public boolean isObject() {
		return true;
	}
	@Override
	public String getClassName() {
		return "java.lang.String";
	}
	
}
