package jjvm;

public class SampleClass implements Runnable {
	
	public int val1=5;
	public double val2=5.2;
	public long val3=5678L;
	public static final float val4=5.3f;
	public static final String val5="Nope\t!";
	
	@Override
	public void run() {
		System.out.println(val1);
		System.out.println(val2);
		System.out.println(val3);
		System.out.println(val4+" "+val5);
		System.out.println(2+2);
	}
	
	public static void main(String... args) {
		Runnable runValue=new SampleClass();
		runValue.run();
	}
	
}
