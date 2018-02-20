package com.github.natnatMc.jjvm;

public abstract class JObject {
	
	@Override
	public abstract String toString();
	
	public abstract boolean isNumber();
	
	public abstract JDecimal toDecimal();
	public abstract JInteger toInteger();
	public abstract long toLong();
	public abstract double toDouble();
	public abstract int toInt();
	public abstract float toFloat();
	public abstract char toChar();
	public abstract short toShort();
	public abstract boolean toBoolean();
	public abstract byte toByte();
	
	public abstract boolean isObject();
	public abstract String getClassName();
	
}
