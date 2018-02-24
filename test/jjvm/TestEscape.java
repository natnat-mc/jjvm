package jjvm;

import com.github.natnatMc.jjvm.source.BytecodeAssembler;

public class TestEscape {
	
	public static void main(String[] args) {
		String text="this\nis\ba\ttext with \\backslashes\\.";
		text+="\nNow with more\t\\\\backslashes\\\\!";
		String escaped=BytecodeAssembler.escapeJava(text);
		String unescaped=BytecodeAssembler.unescapeJava(escaped);
		
		System.out.println("Original text: "+text);
		System.out.println("Escaped text: "+escaped);
		System.out.println("Unescaped text: "+unescaped);
		
		if(text.equals(unescaped)) {
			System.out.println("Escape/unescape success");
		} else {
			System.out.println("Escape/Unescape failure");
		}
	}
	
}
