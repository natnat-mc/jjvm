package jjvm;

public class SampleClass implements Runnable {
	
	public int val1=5;
	//public double val2=5.2;	//FIXME it doesn't accept doubles
	//public long val3=56L;	//FIXME it doesn't accept longs
	public float val4=5.3f;
	public String val5="Nope\t!";
	
	@Override
	public void run() {
		System.out.println(val1);
		//System.out.println(val2);
		//System.out.println(val3);
		System.out.println(val4+" "+val5);
		System.out.println(2+2);
	}
	
}
