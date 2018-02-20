package exceptions;

public class MalformedClassException extends Exception {

	public MalformedClassException(String a) {
		super(a);
	}
	public MalformedClassException(String a, Throwable t) {
		super(a, t);
	}
	public MalformedClassException(Throwable t) {
		super(t);
	}
	
}
