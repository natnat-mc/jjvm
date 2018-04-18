package com.github.natnatMc.jjvm.intermediate;

import java.util.*;
import java.io.*;

public class JOpCode {
	
	protected int opCode;
	protected String mnemonic;
	
	protected char[] arguments;
	protected char[] wideArguments;
	protected boolean varArg;
	protected boolean fromPool;
	
	protected static Map<Integer, JOpCode> byOpCode;
	protected static Map<String, JOpCode> byMnemonic;
	
	public int getOpCode() {
		return opCode;
	}
	public String getMnemonic() {
		return mnemonic;
	}
	public char[] getArguments() {
		if(arguments==null) return null;
		char[] args=new char[arguments.length];
		System.arraycopy(arguments, 0, args, 0, arguments.length);
		return args;
	}
	public char[] getWideArguments() {
		if(wideArguments==null) return null;
		char[] args=new char[wideArguments.length];
		System.arraycopy(wideArguments, 0, args, 0, wideArguments.length);
		return args;
	}
	public boolean isVarArg() {
		return varArg;
	}
	public boolean isFromPool() {
		return fromPool;
	}
	public static JOpCode getByOpCode(int code) {
		return byOpCode.get(code);
	}
	public static JOpCode getByMnemonic(String mnemonic) {
		return byMnemonic.get(mnemonic);
	}
	
	public static void readFromFile(File inFile) throws IOException {
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
		byOpCode=new HashMap<Integer, JOpCode>();
		byMnemonic=new HashMap<String, JOpCode>();
		do {
			String line=reader.readLine();
			if(line==null) break;
			if(line.trim().isEmpty()) continue;
			if(line.startsWith("#")) continue;
			
			String[] parts=line.split("\t+");
			JOpCode opCode=new JOpCode();
			
			opCode.opCode=Integer.decode(parts[0].trim());
			opCode.mnemonic=parts[1].trim();
			
			String[] args=parts[2].trim().split(",");
			opCode.arguments=new char[args.length];
			for(int i=0; i<args.length; i++) {
				if(args[i].equals("n")) {
					opCode.arguments=new char[0];
				} else if(args[i].equals("-1")) {
					opCode.varArg=true;
					opCode.arguments=new char[0];
				} else {
					opCode.arguments[i]=readArg(args[i]);
				}
			}
			
			args=parts[3].trim().split(",");
			opCode.wideArguments=new char[args.length];
			for(int i=0; i<args.length; i++) {
				if(args[i].equals("-1")) {
					opCode.wideArguments=null;
				} else {
					opCode.wideArguments[i]=readArg(args[i]);
				}
			}
			
			if(parts.length>=5) {
				parts[4]=parts[4].trim();
				opCode.fromPool=parts[4].charAt(0)=='y';
			}
			
			byOpCode.put(opCode.opCode, opCode);
			byMnemonic.put(opCode.mnemonic, opCode);
		} while(true);
		reader.close();
	}
	
	private static char readArg(String arg) {
		switch(arg) {
			case "ub":
				return 'U';
			case "b":
				return 'B';
			case "c":
				return 'C';
			case "i":
				return 'I';
			case "s":
				return 'S';
			case "0":
				return '0';
		}
		return 0;
	}
	
}
