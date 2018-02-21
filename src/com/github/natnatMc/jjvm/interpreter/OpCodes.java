package com.github.natnatMc.jjvm.interpreter;

public class OpCodes {
	//reference handling
	public static final byte aaload=		(byte) 0x32;	//load reference from array
	public static final byte aastore=		(byte) 0x53;	//store reference into array
	public static final byte aconst_null=	(byte) 0x01;	//push null onto stack
	public static final byte aload=			(byte) 0x19;	//load reference from local variable
	public static final byte aload_0=		(byte) 0x2a;
	public static final byte aload_1=		(byte) 0x2b;
	public static final byte aload_2=		(byte) 0x2c;
	public static final byte aload_3=		(byte) 0x2d;
	public static final byte anewarray=		(byte) 0xbd;	//create new array of reference
	public static final byte areturn=		(byte) 0xb0;	//return reference from method
	public static final byte arraylength=	(byte) 0xbe;	//get length of array
	public static final byte astore=		(byte) 0x34;	//store reference into local variable
	public static final byte astore_0=		(byte) 0x4b;
	public static final byte astore_1=		(byte) 0x4c;
	public static final byte astore_2=		(byte) 0x4d;
	public static final byte astore_3=		(byte) 0x4e;
	public static final byte athrow=		(byte) 0xbf;	//throw exception
	
	//byte and boolean handling
	public static final byte baload=		(byte) 0x33;	//load byte or boolean from array
	public static final byte bastore=		(byte) 0x54;	//store byte or boolean into array
	public static final byte bipush=		(byte) 0x10;	//push byte onto stack
	
	//char handling
	public static final byte caload=		(byte) 0x34;	//load char from array
	public static final byte castore=		(byte) 0x55;	//store char into array
	public static final byte checkcast=		(byte) 0xc0;	//check whether object is of given type
	
	//double handling
	public static final byte d2f=			(byte) 0x90;	//convert double to float
	public static final byte d2i=			(byte) 0x8e;	//convert double to int
	public static final byte d2l=			(byte) 0x8f;	//convert double to long
	public static final byte dadd=			(byte) 0x63;	//add double
	public static final byte daload=		(byte) 0x31;	//load double from array
	public static final byte dastore=		(byte) 0x52;	//store double into array
	public static final byte dcmpg=			(byte) 0x98;	//compare double
	public static final byte dcmpl=			(byte) 0x97;	//compare double
	public static final byte dconst_0=		(byte) 0x0e;	//push 0.0 onto stack
	public static final byte dconst_1=		(byte) 0x0f;	//push 1.0 onto stack
	public static final byte ddiv=			(byte) 0x6f;	//divide double
	public static final byte dload=			(byte) 0x18;	//load double from local variable
	public static final byte dload_0=		(byte) 0x26;
	public static final byte dload_1=		(byte) 0x27;
	public static final byte dload_2=		(byte) 0x28;
	public static final byte dload_3=		(byte) 0x29;
	public static final byte dmul=			(byte) 0x6b;	//multiply double
	public static final byte dneg=			(byte) 0x77;	//negate double
	public static final byte drem=			(byte) 0x73;	//remainder double
	public static final byte dreturn=		(byte) 0xaf;	//return double from method
	public static final byte dstore=		(byte) 0x39;	//store double into local variable
	public static final byte dstore_0=		(byte) 0x47;
	public static final byte dstore_1=		(byte) 0x48;
	public static final byte dstore_2=		(byte) 0x49;
	public static final byte dstore_3=		(byte) 0x4a;
	public static final byte dsub=			(byte) 0x67;	//subtract double
	
	//stack handling
	public static final byte dup=			(byte) 0x59;	//duplicate top stack value
	public static final byte dup_x1=		(byte) 0x5a;	//duplicate top stack value, insert two down
	public static final byte dup_x2=		(byte) 0x5b;	//duplicate top stack value, insert 2/3 down
	public static final byte dup2=			(byte) 0x5c;	//duplicate top 1/2 stack values
	public static final byte dup2_x1=		(byte) 0x5d;	//duplicate top 1/2 stack value, insert 2/3 down
	public static final byte dup2_x2=		(byte) 0x5e;	//duplicate top 1/2 stack value, insert 2/3/4 down
	
	//float handling
	public static final byte f2d=			(byte) 0x8d;	//convert float to double
	public static final byte f2i=			(byte) 0x8b;	//convert float to int
	public static final byte f2l=			(byte) 0x8c;	//convert float to long
	public static final byte fadd=			(byte) 0x62;	//add float
	public static final byte faload=		(byte) 0x30;	//load float from array
	public static final byte fastore=		(byte) 0x51;	//store float into array
	public static final byte fcmpg=			(byte) 0x96;	//compare float
	public static final byte fcmpl=			(byte) 0x95;	//compare float
	public static final byte fconst_0=		(byte) 0x0b;	//push 0.0f onto stack
	public static final byte fconst_1=		(byte) 0x0c;	//push 1.0f onto stack
	public static final byte fconst_2=		(byte) 0x0d;	//push 2.0f onto stack
	public static final byte fdiv=			(byte) 0x6e;	//divide float
	public static final byte fload=			(byte) 0x17;	//load float from local variable
	public static final byte fload_0=		(byte) 0x22;
	public static final byte fload_1=		(byte) 0x23;
	public static final byte fload_2=		(byte) 0x24;
	public static final byte fload_3=		(byte) 0x25;
	public static final byte fmul=			(byte) 0x6a;	//multiply float
	public static final byte fneg=			(byte) 0x76;	//negate float
	public static final byte frem=			(byte) 0x72;	//remainder float
	public static final byte freturn=		(byte) 0xae;	//return float from method
	public static final byte fstore=		(byte) 0x38;	//store float into local variable
	public static final byte fstore_0=		(byte) 0x43;
	public static final byte fstore_1=		(byte) 0x44;
	public static final byte fstore_2=		(byte) 0x45;
	public static final byte fstore_3=		(byte) 0x46;
	public static final byte fsub=			(byte) 0x66;	//subtract float
	
	//field handling
	public static final byte getfield=		(byte) 0xb4;	//get field from object
	public static final byte getstatic=		(byte) 0xb2;	//get static field from class
	
	//jump
	public static final byte goto_=			(byte) 0xa7;	//jump somewhere
	public static final byte goto_w=		(byte) 0xc8;	//jump somewhere, 32bit index
	
	//int handling
	public static final byte i2b=			(byte) 0x91;	//convert int to byte
	public static final byte i2c=			(byte) 0x92;	//convert char to byte
	public static final byte i2d=			(byte) 0x87;	//convert int to double
	public static final byte i2f=			(byte) 0x86;	//convert int to float
	public static final byte i2l=			(byte) 0x85;	//convert int to long
	public static final byte i2s=			(byte) 0x93;	//convert int to short
	public static final byte iadd=			(byte) 0x60;	//add int
	public static final byte iaload=		(byte) 0x2e;	//load int from array
	public static final byte iand=			(byte) 0x7e;	//bitwise and int
	public static final byte iastore=		(byte) 0x4f;	//store int into array
	public static final byte iconst_m1=		(byte) 0x02;	//push -1 onto stack
	public static final byte iconst_0=		(byte) 0x03;	//push 0 onto stack
	public static final byte iconst_1=		(byte) 0x04;	//push 1 onto stack
	public static final byte iconst_2=		(byte) 0x05;	//push 2 onto stack
	public static final byte iconst_3=		(byte) 0x06;	//push 3 onto stack
	public static final byte iconst_4=		(byte) 0x07;	//push 4 onto stack
	public static final byte iconst_5=		(byte) 0x08;	//push 5 onto stack
	public static final byte idiv=			(byte) 0x6c;	//divide int
	
	//jump
	public static final byte if_acmpeq=		(byte) 0xa5;	//jump if equal reference
	public static final byte if_acmpne=		(byte) 0xa6;	//jump if not equal reference
	public static final byte if_icmpeq=		(byte) 0x9f;	//jump if equal int
	public static final byte if_icmpne=		(byte) 0xa0;	//jump if not equal int
	public static final byte if_icmplt=		(byte) 0xa1;	//jump if less than int
	public static final byte if_icmpge=		(byte) 0xa2;	//jump if greater or equal int
	public static final byte if_icmpgt=		(byte) 0xa3;	//jump if grater than int
	public static final byte if_icmple=		(byte) 0xa4;	//jump if less or equal int
	public static final byte ifeq=			(byte) 0x99;	//jump if zero int
	public static final byte ifne=			(byte) 0x9a;	//jump if nonzero int
	public static final byte iflt=			(byte) 0x9b;	//jump if less than zero int
	public static final byte ifge=			(byte) 0x9c;	//jump if greater or equal zero int
	public static final byte ifgt=			(byte) 0x9d;	//jump if greater than zero int
	public static final byte ifle=			(byte) 0x9e;	//jump if less or equal zero int
	public static final byte ifnonnull=		(byte) 0xc7;	//jump if reference not nul
	public static final byte ifnull=		(byte) 0xc6;	//jump if reference is null
	
	//int handling
	public static final byte iinc=			(byte) 0x84;	//increment local variable by constant int
	public static final byte iload=			(byte) 0x15;	//load int from local variable
	public static final byte iload_0=		(byte) 0x1a;
	public static final byte iload_1=		(byte) 0x1b;
	public static final byte iload_2=		(byte) 0x1c;
	public static final byte iload_3=		(byte) 0x1d;
	public static final byte imul=			(byte) 0x68;	//multiply int
	public static final byte ineg=			(byte) 0x74;	//negate int
	
	//object handling
	public static final byte instanceof_=	(byte) 0xc1;	//check if reference is of given type
	public static final byte invokedynamic=	(byte) 0xba;	//invoke dynamic method
	public static final byte invokeinterface=	(byte) 0xb9;	//invoke interface method
	public static final byte invokespecial=	(byte) 0xb7;	//invoke instance method
	public static final byte invokestatic=	(byte) 0xb8;	//invoke static method
	public static final byte invokevirtual=	(byte) 0xb6;	//invoke method, dispatched
	
	//int handling
	public static final byte ior=			(byte) 0x80;	//bitwise or int
	public static final byte irem=			(byte) 0x70;	//remainder int
	public static final byte ireturn=		(byte) 0xac;	//return int from method
	public static final byte ishl=			(byte) 0x78;	//shift left int
	public static final byte ishr=			(byte) 0x7a;	//arithmetic shift right int
	public static final byte istore=		(byte) 0x36;	//store int into local variable
	public static final byte istore_0=		(byte) 0x3b;
	public static final byte istore_1=		(byte) 0x3c;
	public static final byte istore_2=		(byte) 0x3d;
	public static final byte istore_3=		(byte) 0x3e;
	public static final byte isub=			(byte) 0x64;	//subtract int
	public static final byte iushr=			(byte) 0x7c;	//logical shift right int
	public static final byte ixor=			(byte) 0x82;	//bitwise xor int
	
	//jump
	public static final byte jsr=			(byte) 0xa8;	//jump subroutine
	public static final byte jsr_w=			(byte) 0xc9;	//jump subroutine, 32bit index
	
	//long handling
	public static final byte l2d=			(byte) 0x8a;	//convert long to double
	public static final byte l2f=			(byte) 0x89;	//convert long to float
	public static final byte l2i=			(byte) 0x88;	//convert long to int
	public static final byte ladd=			(byte) 0x61;	//add long
	public static final byte laload=		(byte) 0x2f;	//load long from array
	public static final byte land=			(byte) 0x7f;	//bitwise and long
	public static final byte lastore=		(byte) 0x50;	//store long into array
	public static final byte lcmp=			(byte) 0x94;	//compare long
	public static final byte lconst_0=		(byte) 0x09;	//push 0l onto stack
	public static final byte lconst_1=		(byte) 0x0a;	//push 1l onto stack
	
	//stack handling
	public static final byte ldc=			(byte) 0x12;	//push item from constant pool, 8bit index
	public static final byte ldc_w=			(byte) 0x13;	//push item from constant pool
	public static final byte ldc2_w=		(byte) 0x14;	//push long or double from constant pool
	
	//long handling
	public static final byte ldiv=			(byte) 0x6d;	//divide long
	public static final byte lload=			(byte) 0x16;	//load long from local variable
	public static final byte lload_0=		(byte) 0x1e;
	public static final byte lload_1=		(byte) 0x1f;
	public static final byte lload_2=		(byte) 0x20;
	public static final byte lload_3=		(byte) 0x21;
	public static final byte lmul=			(byte) 0x69;	//multiply long
	public static final byte lneg=			(byte) 0x75;	//negate long
	
	//switch
	public static final byte lookupswitch=	(byte) 0xab;	//jump table
	
	//long handling
	public static final byte lor=			(byte) 0x81;	//bitwise long or
	public static final byte lrem=			(byte) 0x71;	//remainder long
	public static final byte lreturn=		(byte) 0xad;	//return long from method
	public static final byte lshl=			(byte) 0x79;	//shift left long
	public static final byte lshr=			(byte) 0x7b;	//arithmetic shift right long
	public static final byte lstore=		(byte) 0x37;	//store long into local variable
	public static final byte lstore_0=		(byte) 0x3f;
	public static final byte lstore_1=		(byte) 0x40;
	public static final byte lstore_2=		(byte) 0x41;
	public static final byte lstore_3=		(byte) 0x42;
	public static final byte lsub=			(byte) 0x65;	//subtract long
	public static final byte lushr=			(byte) 0x7d;	//logical shift right long
	public static final byte lxor=			(byte) 0x83;	//biwise xor long
	
	//object handling
	public static final byte monitorenter=	(byte) 0xc2;	//enter monitor for object
	public static final byte monitorexit=	(byte) 0xc3;	//exit monitor for object
	public static final byte multianewarray=	(byte) 0xc5;	//create multidimensional array
	public static final byte new_=			(byte) 0xbb;	//create new object
	public static final byte newarray=		(byte) 0xbc;	//create new array
	
	//flow control
	public static final byte nop=			(byte) 0x00;	//do nothing
	
	//stack handling
	public static final byte pop=			(byte) 0x57;	//pops a value from the stack
	public static final byte pop2=			(byte) 0x58;	//pops 1/2 value from the stack
	
	//object handling
	public static final byte putfield=		(byte) 0xb5;	//set field in object
	public static final byte putstatic=		(byte) 0xb3;	//set static field
	
	//flow control
	public static final byte ret=			(byte) 0xa9;	//return from subroutine
	public static final byte return_=		(byte) 0xb1;	//return void from method
	
	//short handling
	public static final byte saload=		(byte) 0x35;	//load short from array
	public static final byte sastore=		(byte) 0x56;	//store short into array
	public static final byte sipush=		(byte) 0x11;	//push short onto stack
	
	//stack handling
	public static final byte swap=			(byte) 0x5f;	//swap two stack values
	
	//switch
	public static final byte tableswitch=	(byte) 0xaa;	//jump table
	
	//misc
	public static final byte wide=			(byte) 0xc4;	//read local variable index with 16bit
}
