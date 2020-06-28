package com.cy.java.references;

import java.lang.ref.WeakReference;

class Point{
	/**对象在被回收时会调用finalize()*/
	@Override
	protected void finalize() throws Throwable {
		System.out.println("==finalize()==");
	}
}
//-Xmx5m -Xms5m -XX:+PrintGCDetails
public class ReferencesTests {
	public static void main(String[] args) {
		//GC系统为JVM系统中的一个子系统，此系统在内存不足时会自动启动垃圾回收机制，对系统中垃圾对象对象进行回收。
		//GC系统会认为哪些对象为垃圾对象?(没有引用引用的对象->系统底层会对对象进行可达性分析)
		//1.强引用(此类型的引用引用的对象在任何时候都不会被GC)
		//Point p1=new Point();//p1为一个引用变量，指向等号右边的对象，p1为一个强引用
		//Point p2=p1;
		//p1=null; //思考假如Cache存储对象时用的是强引用，它本身又没有设计缓存淘汰算法。
		
		//2.软引用(当GC系统启动时，此引用引用的对象可能会被回收，在内存相对吃紧的时候对象有可能就被回收了)
		//SoftReference<Point> sr=new SoftReference<Point>(new Point());
		//System.out.println(sr.get());//通过get方法获取对象
		
		//3.弱引用(弱引用引用的对象的生命力相对于软引用更弱一些，只要触发了GC此引用引用的对象就可能会被回收)
		WeakReference<Point> wr=new WeakReference<Point>(new Point());
		System.out.println(wr.get());//通过get方法获取对象
		
		
		//手动启动GC
		//System.gc();
		//自动GC(JVM系统随着对象的创建和销毁会自动启动GC系统)
		byte[] a1=new byte[1024*1024];
		byte[] a2=new byte[1024*1024];
		byte[] a3=new byte[1024*1024];
		byte[] a4=new byte[1024*1024];
		
	}
	
}
