package com.github.natnatMc.jjvm;

public class JDecimal extends JObject {
	
	private double value;
	
	public JDecimal(double d) {
		this.value=d;
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}
	
	@Override
	public boolean isNumber() {
		return true;
	}
	
	@Override
	public JDecimal toDecimal() {
		return this;
	}
	@Override
	public JInteger toInteger() {
		return new JInteger((long) value);
	}
	@Override
	public long toLong() {
		return (long) value;
	}
	@Override
	public double toDouble() {
		return value;
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
		return value!=0.0;
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
