package jjvm;

import com.github.natnatMc.jjvm.struct.ClassFlags;

public class TestClasses {
	
	public static void main(String[] args) {
		testClass(new int[0].getClass());
		testClass(int.class);
		testClass(void.class);
		testClass(TestClasses.class);
	}
	
	public static void testClass(Class<?> cl) {
		System.out.print(ClassFlags.toStringClass(cl.getModifiers()));
		System.out.print(" class ");
		System.out.println(cl.getName());
		
		if(cl.isArray()) {
			System.out.println("Is an array type");
		}
		if(cl.isPrimitive()) {
			System.out.println("Is a primitive type");
		}
		if(cl.isAnnotation()) {
			System.out.println("Is an annotation type");
		} else if(cl.isInterface()) {
			System.out.println("Is an interface type");
		}
		if(cl.isAnonymousClass()) {
			System.out.println("Is an anonymous type");
		}
		if(cl.isEnum()) {
			System.out.println("Is an enumeration type");
		}
		if(cl.isSynthetic()) {
			System.out.println("Is a synthetic class");
		}
		
		Class<?>[] ifaces=cl.getInterfaces();
		if(ifaces.length!=0) {
			System.out.println("Interfaces:");
			for(int i=0; i<ifaces.length; i++) {
				System.out.print('\t');
				System.out.println(ifaces[i].getName());
			}
		} else {
			System.out.println("No interfaces");
		}
		
		Class<?> sup=cl.getSuperclass();
		if(sup!=null) {
			System.out.println("Superclass:");
			System.out.print('\t');
			System.out.println(sup.getName());
		} else {
			System.out.println("No superclass");
		}
		
		System.out.println();
	}
	
}
