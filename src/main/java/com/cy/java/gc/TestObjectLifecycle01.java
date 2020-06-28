package com.cy.java.gc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Point{
	//对象回收之前执行(可以在方法内部实现资源释放或监控对象是否要销毁了)
	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalize()");
	}
}
//OutOfMemoryError
//监控垃圾回收是否启动，打印GC详细信息
//JVM 常用参数
//最大堆-Xmx5m 
//最小堆-Xms5m 
//打印GC 详细信息 -XX:+PrintGCDetails
public  class TestObjectLifecycle01 {
	static Map<String,Object> beanPool=new HashMap<>();
	public static void main(String[] args) {
		Point p1=new Point();//强引用
		//singleton
		beanPool.put("point", p1);
		//...
		//prototype
		p1=null;//没有引用再指向Point对象了，Point对象此时为一个不可达对象
		beanPool.clear();
		//手动启动GC
		//System.gc();
		//自动启动GC(随着内存中对象的增加，内存达到一定标准，会自动进行GC)
		List<byte[]> list=new ArrayList<>();
		//for(int i=0;i<1000;i++) {
		    list.add(new byte[1024*1024]);
			list.add(new byte[1024*1024]);
			list.add(new byte[1024*1024]);
			list.add(new byte[1024*1024]);
		//}		
	}
}
//编程目标：业务，高效，低耗


