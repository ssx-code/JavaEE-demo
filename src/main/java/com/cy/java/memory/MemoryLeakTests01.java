package com.cy.java.memory;

import com.cy.java.memory.Outer.StaticInnerThread;
//import static com.cy.java.memory.Outer.StaticInnerThread;

/**
 * 内存中的对象不用了但还占用着内存，此对象没有被回收，这种现象称之为内存泄漏。
 * @author qilei
 */
class Outer{
	/**
	 * 实例内部类对象会默认引用外部类对象，并且实例内部类对象的存在，首先需要构建外部类对象
	 * @author qilei
	 *
	 */
	class InstanceInnerThread extends Thread{
		public void run() {
			
			while(true) {}
		};
	}
	/**
	 * 静态内部类对象不会引用外部类对象
	 */
	static class StaticInnerThread extends Thread{
		public void run() {
			while(true) {}
		};
	}
	@Override
	protected void finalize() throws Throwable {
		System.out.println("==finalize()==");
	}
}
public class MemoryLeakTests01 {
	public static void main(String[] args) {
		//testInstanceInnerMemoryLeak();
		
		testStaticInnerMemoryLeak();
	}

	private static void testStaticInnerMemoryLeak() {
		Outer outer2=new Outer();
		new Outer.StaticInnerThread().start();
		outer2=null;
		//手动启动gc
		System.gc();
	}

	private static void testInstanceInnerMemoryLeak() {
		Outer outer1=new Outer();
		outer1.new InstanceInnerThread().start();
		outer1=null;
		//手动GC,此时Outer对象并不会被回收，因为还有一个实例内部类引用着外部类对象，此时会出现内存泄漏
		System.gc();
	}
}
