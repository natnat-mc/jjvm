package com.github.natnatMc.jjvm.types;

public class JInteger extends JObject {
	
	private long value;
	
	public JInteger(long d) {
		this.value=d;
	}
	
	@Override
	public String toString() {
		return Long.toString(value);
	}
	
	@Override
	public boolean isNumber() {
		return true;
	}
	@Override
	public JDecimal toDecimal() {
		return new JDecimal((double) value);
	}
	@Override
	public JInteger toInteger() {
		return this;
	}
	@Override
	public long toLong() {
		return value;
	}
	@Override
	public double toDouble() {
		return (double) value;
	}
	@Override
	public int toInt() {
		return (int) value;
	}
	@Override
	public float toFloat() {
		return (float) value;
	}
	@Override
	public char toChar() {
		return (char) value;
	}
	@Override
	public short toShort() {
		return (short) value;
	}
	@Override
	public boolean toBoolean() {
		return value!=0L;
	}
	@Override
	public byte toByte() {
		return (byte) value;
	}
	
	@Override
	public boolean isObject() {
		return false;
	}
	@Override
	public String getClassName() {
		return null;
	}
	
}
