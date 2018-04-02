package jjvm;

import com.github.natnatMc.jjvm.source.BytecodeAssembler;

public class TestEscape {
	
	public static void main(String[] args) {
		String first="this\nis\ba\ttext with \\backslashes and : colons, semi;colons\\.\nNow with more\t\\\\backslashes\\\\!";
		String colon=":";
		
		if(test(first)&&test(colon)) {
			System.out.println("Escape/unescape success");
		} else {
			System.out.println("Escape/Unescape failure");
		}
	}
	
	public static boolean test(String text) {
		System.out.println("Original text: "+text);
		String escaped=BytecodeAssembler.escapeJava(text);
		System.out.println("Escaped text: "+escaped);
		String unescaped=BytecodeAssembler.unescapeJava(escaped);
		System.out.println("Unescaped text: "+unescaped);
		StringBuilder builder=new StringBuilder();
		int readString=BytecodeAssembler.readString(builder, '"'+escaped+'"');
		System.out.println("Read text: "+builder.toString()+" ("+readString+")");
		return text.equals(unescaped)&&escaped.equals(builder.toString());
	}
	
}
