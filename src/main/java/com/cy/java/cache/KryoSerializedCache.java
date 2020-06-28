package com.cy.java.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.sql.rowset.serial.SerialException;

/**
 * 序列化cache
 * 1)对象存储到cache之前先进行序列化，让后进行存储，存储cache的对象是一个字节数组的引用。
 * 2)从cache获取对象需要将字节数组反序列化。
 * @author qilei
 *
 */
public class KryoSerializedCache implements Cache {

	private Cache cache;
	public KryoSerializedCache(Cache cache) {
		this.cache=cache;
	}
	
	@Override
	public void putObject(Object key, Object value) {
		//1.将对象序列化
		byte[] array=KryoUtils.serialize(value);
		//2.将序列化后的字节数组引用存储到cache
		cache.putObject(key,array);
	}

	@Override
	public Object getObject(Object key) {
		//1.基于key获取缓存中的字节数组引用
		byte[] array=(byte[])cache.getObject(key);
		//2.将字节数组反序列化为对象
		return KryoUtils.deserialize(array);
	}

	@Override
	public Object removeObject(Object key) {
		return KryoUtils.deserialize((byte[])cache.removeObject(key));
	}

	@Override
	public void clear() {
		cache.clear();
	}
	@Override
	public int size() {
		return cache.size();
	}
	public static void main(String[] args) {
		Cache cache=new KryoSerializedCache(new PerpetualCache());
		cache.putObject("A", 500);
		Object a1=cache.getObject("A");
		Object a2=cache.getObject("A");
		System.out.println("a1="+a1);
		System.out.println("a2="+a2);
		System.out.println(a1==a2);//false
	}

}
