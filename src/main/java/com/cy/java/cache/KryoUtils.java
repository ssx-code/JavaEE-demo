package com.cy.java.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoUtils {

	/**
	 * 多线程并发执行时，可能会出现线程不安全，具体原因是什么？
	 * 1)多个线程的并发
	 * 2)多个线程有数据共享
	 * 3)多个线程在共享数据集上的操作不是原子操作
	 * 
	 * 分析：当出现了线程不安全，如何进行修改来保证线程安全
	 * 1)将多线程改为单线程。
	 * 2)取消共享 (例如在当前应用中我们一个线程一个Kryo对象)
	 * 3)加锁+CAS
	 * 
	 * ThreadLocal提供了这样的一种机制：
	 * 1)可以将对象绑定到当前线程（其实是将对象存储到当前线程的map中）
	 * 2)可以从当前线程获取绑定的对象(从当前线程的map中获取对象)
	 */
	static private final ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
		   protected Kryo initialValue() {
		      Kryo kryo = new Kryo();
		      // Configure the Kryo instance.
		      kryo.setRegistrationRequired(false);
		      //....
		      return kryo;
		   };
		};
	public static Object deserialize(byte[] array){
		 Kryo kryo=kryos.get();
		 Input input = new Input(new ByteArrayInputStream(array));
		 Object obj=kryo.readClassAndObject(input);
		 return obj;
	}
	public static byte[] serialize(Object object){
		//从当前线程获取kryo对象，当前线程没有会调用ThreadLocal的initialValue方法创建对象并绑定线程
		 Kryo kryo=kryos.get();
		 ByteArrayOutputStream bos=new ByteArrayOutputStream();
		 Output output = new Output(bos);
	     kryo.writeClassAndObject(output, object);
	     output.close();
		return bos.toByteArray();
	}
}
