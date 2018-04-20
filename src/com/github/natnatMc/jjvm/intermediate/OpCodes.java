package com.github.natnatMc.jjvm.intermediate;

public class OpCodes {
	//reference handling
	public static final int aaload=			0x32;	//load reference from array
	public static final int aastore=		0x53;	//store reference into array
	public static final int aconst_null=	0x01;	//push null onto stack
	public static final int aload=			0x19;	//load reference from local variable
	public static final int aload_0=		0x2a;
	public static final int aload_1=		0x2b;
	public static final int aload_2=		0x2c;
	public static final int aload_3=		0x2d;
	public static final int anewarray=		0xbd;	//create new array of reference
	public static final int areturn=		0xb0;	//return reference from method
	public static final int arraylength=	0xbe;	//get length of array
	public static final int astore=			0x3a;	//store reference into local variable
	public static final int astore_0=		0x4b;
	public static final int astore_1=		0x4c;
	public static final int astore_2=		0x4d;
	public static final int astore_3=		0x4e;
	public static final int athrow=			0xbf;	//throw exception
	
	//int and boolean handling
	public static final int baload=			0x33;	//load byte or boolean from array
	public static final int bastore=		0x54;	//store byte or boolean into array
	public static final int bipush=			0x10;	//push byte onto stack
	
	//char handling
	public static final int caload=			0x34;	//load char from array
	public static final int castore=		0x55;	//store char into array
	public static final int checkcast=		0xc0;	//check whether object is of given type
	
	//double handling
	public static final int d2f=			0x90;	//convert double to float
	public static final int d2i=			0x8e;	//convert double to int
	public static final int d2l=			0x8f;	//convert double to long
	public static final int dadd=			0x63;	//add double
	public static final int daload=			0x31;	//load double from array
	public static final int dastore=		0x52;	//store double into array
	public static final int dcmpg=			0x98;	//compare double
	public static final int dcmpl=			0x97;	//compare double
	public static final int dconst_0=		0x0e;	//push 0.0 onto stack
	public static final int dconst_1=		0x0f;	//push 1.0 onto stack
	public static final int ddiv=			0x6f;	//divide double
	public static final int dload=			0x18;	//load double from local variable
	public static final int dload_0=		0x26;
	public static final int dload_1=		0x27;
	public static final int dload_2=		0x28;
	public static final int dload_3=		0x29;
	public static final int dmul=			0x6b;	//multiply double
	public static final int dneg=			0x77;	//negate double
	public static final int drem=			0x73;	//remainder double
	public static final int dreturn=		0xaf;	//return double from method
	public static final int dstore=			0x39;	//store double into local variable
	public static final int dstore_0=		0x47;
	public static final int dstore_1=		0x48;
	public static final int dstore_2=		0x49;
	public static final int dstore_3=		0x4a;
	public static final int dsub=			0x67;	//subtract double
	
	//stack handling
	public static final int dup=			0x59;	//duplicate top stack value
	public static final int dup_x1=			0x5a;	//duplicate top stack value, insert two down
	public static final int dup_x2=			0x5b;	//duplicate top stack value, insert 2/3 down
	public static final int dup2=			0x5c;	//duplicate top 1/2 stack values
	public static final int dup2_x1=		0x5d;	//duplicate top 1/2 stack value, insert 2/3 down
	public static final int dup2_x2=		0x5e;	//duplicate top 1/2 stack value, insert 2/3/4 down
	
	//float handling
	public static final int f2d=			0x8d;	//convert float to double
	public static final int f2i=			0x8b;	//convert float to int
	public static final int f2l=			0x8c;	//convert float to long
	public static final int fadd=			0x62;	//add float
	public static final int faload=			0x30;	//load float from array
	public static final int fastore=		0x51;	//store float into array
	public static final int fcmpg=			0x96;	//compare float
	public static final int fcmpl=			0x95;	//compare float
	public static final int fconst_0=		0x0b;	//push 0.0f onto stack
	public static final int fconst_1=		0x0c;	//push 1.0f onto stack
	public static final int fconst_2=		0x0d;	//push 2.0f onto stack
	public static final int fdiv=			0x6e;	//divide float
	public static final int fload=			0x17;	//load float from local variable
	public static final int fload_0=		0x22;
	public static final int fload_1=		0x23;
	public static final int fload_2=		0x24;
	public static final int fload_3=		0x25;
	public static final int fmul=			0x6a;	//multiply float
	public static final int fneg=			0x76;	//negate float
	public static final int frem=			0x72;	//remainder float
	public static final int freturn=		0xae;	//return float from method
	public static final int fstore=			0x38;	//store float into local variable
	public static final int fstore_0=		0x43;
	public static final int fstore_1=		0x44;
	public static final int fstore_2=		0x45;
	public static final int fstore_3=		0x46;
	public static final int fsub=			0x66;	//subtract float
	
	//field handling
	public static final int getfield=		0xb4;	//get field from object
	public static final int getstatic=		0xb2;	//get static field from class
	
	//jump
	public static final int goto_=			0xa7;	//jump somewhere
	public static final int goto_w=			0xc8;	//jump somewhere, 32bit index
	
	//int handling
	public static final int i2b=			0x91;	//convert int to byte
	public static final int i2c=			0x92;	//convert int to char
	public static final int i2d=			0x87;	//convert int to double
	public static final int i2f=			0x86;	//convert int to float
	public static final int i2l=			0x85;	//convert int to long
	public static final int i2s=			0x93;	//convert int to short
	public static final int iadd=			0x60;	//add int
	public static final int iaload=			0x2e;	//load int from array
	public static final int iand=			0x7e;	//bitwise and int
	public static final int iastore=		0x4f;	//store int into array
	public static final int iconst_m1=		0x02;	//push -1 onto stack
	public static final int iconst_0=		0x03;	//push 0 onto stack
	public static final int iconst_1=		0x04;	//push 1 onto stack
	public static final int iconst_2=		0x05;	//push 2 onto stack
	public static final int iconst_3=		0x06;	//push 3 onto stack
	public static final int iconst_4=		0x07;	//push 4 onto stack
	public static final int iconst_5=		0x08;	//push 5 onto stack
	public static final int idiv=			0x6c;	//divide int
	
	//jump
	public static final int if_acmpeq=		0xa5;	//jump if equal reference
	public static final int if_acmpne=		0xa6;	//jump if not equal reference
	public static final int if_icmpeq=		0x9f;	//jump if equal int
	public static final int if_icmpne=		0xa0;	//jump if not equal int
	public static final int if_icmplt=		0xa1;	//jump if less than int
	public static final int if_icmpge=		0xa2;	//jump if greater or equal int
	public static final int if_icmpgt=		0xa3;	//jump if grater than int
	public static final int if_icmple=		0xa4;	//jump if less or equal int
	public static final int ifeq=			0x99;	//jump if zero int
	public static final int ifne=			0x9a;	//jump if nonzero int
	public static final int iflt=			0x9b;	//jump if less than zero int
	public static final int ifge=			0x9c;	//jump if greater or equal zero int
	public static final int ifgt=			0x9d;	//jump if greater than zero int
	public static final int ifle=			0x9e;	//jump if less or equal zero int
	public static final int ifnonnull=		0xc7;	//jump if reference not nul
	public static final int ifnull=			0xc6;	//jump if reference is null
	
	//int handling
	public static final int iinc=			0x84;	//increment local variable by constant int
	public static final int iload=			0x15;	//load int from local variable
	public static final int iload_0=		0x1a;
	public static final int iload_1=		0x1b;
	public static final int iload_2=		0x1c;
	public static final int iload_3=		0x1d;
	public static final int imul=			0x68;	//multiply int
	public static final int ineg=			0x74;	//negate int
	
	//object handling
	public static final int instanceof_=	0xc1;	//check if reference is of given type
	public static final int invokedynamic=	0xba;	//invoke dynamic method
	public static final int invokeinterface=0xb9;	//invoke interface method
	public static final int invokespecial=	0xb7;	//invoke instance method
	public static final int invokestatic=	0xb8;	//invoke static method
	public static final int invokevirtual=	0xb6;	//invoke method, dispatched
	
	//int handling
	public static final int ior=			0x80;	//bitwise or int
	public static final int irem=			0x70;	//remainder int
	public static final int ireturn=		0xac;	//return int from method
	public static final int ishl=			0x78;	//shift left int
	public static final int ishr=			0x7a;	//arithmetic shift right int
	public static final int istore=			0x36;	//store int into local variable
	public static final int istore_0=		0x3b;
	public static final int istore_1=		0x3c;
	public static final int istore_2=		0x3d;
	public static final int istore_3=		0x3e;
	public static final int isub=			0x64;	//subtract int
	public static final int iushr=			0x7c;	//logical shift right int
	public static final int ixor=			0x82;	//bitwise xor int
	
	//jump
	public static final int jsr=			0xa8;	//jump subroutine
	public static final int jsr_w=			0xc9;	//jump subroutine, 32bit index
	
	//long handling
	public static final int l2d=			0x8a;	//convert long to double
	public static final int l2f=			0x89;	//convert long to float
	public static final int l2i=			0x88;	//convert long to int
	public static final int ladd=			0x61;	//add long
	public static final int laload=			0x2f;	//load long from array
	public static final int land=			0x7f;	//bitwise and long
	public static final int lastore=		0x50;	//store long into array
	public static final int lcmp=			0x94;	//compare long
	public static final int lconst_0=		0x09;	//push 0l onto stack
	public static final int lconst_1=		0x0a;	//push 1l onto stack
	
	//stack handling
	public static final int ldc=			0x12;	//push item from constant pool, 8bit index
	public static final int ldc_w=			0x13;	//push item from constant pool
	public static final int ldc2_w=			0x14;	//push long or double from constant pool
	
	//long handling
	public static final int ldiv=			0x6d;	//divide long
	public static final int lload=			0x16;	//load long from local variable
	public static final int lload_0=		0x1e;
	public static final int lload_1=		0x1f;
	public static final int lload_2=		0x20;
	public static final int lload_3=		0x21;
	public static final int lmul=			0x69;	//multiply long
	public static final int lneg=			0x75;	//negate long
	
	//switch
	public static final int lookupswitch=	0xab;	//jump table
	
	//long handling
	public static final int lor=			0x81;	//bitwise long or
	public static final int lrem=			0x71;	//remainder long
	public static final int lreturn=		0xad;	//return long from method
	public static final int lshl=			0x79;	//shift left long
	public static final int lshr=			0x7b;	//arithmetic shift right long
	public static final int lstore=			0x37;	//store long into local variable
	public static final int lstore_0=		0x3f;
	public static final int lstore_1=		0x40;
	public static final int lstore_2=		0x41;
	public static final int lstore_3=		0x42;
	public static final int lsub=			0x65;	//subtract long
	public static final int lushr=			0x7d;	//logical shift right long
	public static final int lxor=			0x83;	//bitwise xor long
	
	//object handling
	public static final int monitorenter=	0xc2;	//enter monitor for object
	public static final int monitorexit=	0xc3;	//exit monitor for object
	public static final int multianewarray=	0xc5;	//create multidimensional array
	public static final int new_=			0xbb;	//create new object
	public static final int newarray=		0xbc;	//create new array
	
	//flow control
	public static final int nop=			0x00;	//do nothing
	
	//stack handling
	public static final int pop=			0x57;	//pops a value from the stack
	public static final int pop2=			0x58;	//pops 1/2 value from the stack
	
	//object handling
	public static final int putfield=		0xb5;	//set field in object
	public static final int putstatic=		0xb3;	//set static field
	
	//flow control
	public static final int ret=			0xa9;	//return from subroutine
	public static final int return_=		0xb1;	//return void from method
	
	//short handling
	public static final int saload=			0x35;	//load short from array
	public static final int sastore=		0x56;	//store short into array
	public static final int sipush=			0x11;	//push short onto stack
	
	//stack handling
	public static final int swap=			0x5f;	//swap two stack values
	
	//switch
	public static final int tableswitch=	0xaa;	//jump table
	
	//misc
	public static final int wide=			0xc4;	//read local variable index with 16bit
}
