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
public class SerializedCache implements Cache {

	private Cache cache;
	public SerializedCache(Cache cache) {
		this.cache=cache;
	}
	/**对象序列化*/
	private byte[] serialize(Object value) {
		//1.构建流对象
		ByteArrayOutputStream bos=null;
		ObjectOutputStream out=null;
		bos=new ByteArrayOutputStream();//内置一个可扩容的数组
		try {
		out=new ObjectOutputStream(bos);//装饰模式
		//2.对象序列化
		out.writeObject(value);//obj对象需要实现Serializable
		out.flush();
		return bos.toByteArray();
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("序列化失败");
		}finally {
		//3.释放资源
		 try{if(out!=null) {out.close();out=null;}}catch(Exception e) {}
		}
	}
	/**对象的反序列化*/
	private Object deserialize(byte[] array){
	    //1.创建流对象
		ByteArrayInputStream bis=null;
		ObjectInputStream ois=null;
		bis=new ByteArrayInputStream(array);
		try {
		ois=new ObjectInputStream(bis);
		//2.对象的反序列化
		Object obj=ois.readObject();
		return obj;
		}catch(Exception e) {
		e.printStackTrace();
		throw new RuntimeException("反序列化失败");
		}finally {
		//3.释放资源
		try{if(bis!=null) {bis.close();bis=null;}}catch(Exception e) {}
		}
	}
	@Override
	public void putObject(Object key, Object value) {
		//1.将对象序列化
		byte[] array=serialize(value);
		//2.将序列化后的字节数组引用存储到cache
		cache.putObject(key,array);
	}

	@Override
	public Object getObject(Object key) {
		//1.基于key获取缓存中的字节数组引用
		byte[] array=(byte[])cache.getObject(key);
		//2.将字节数组反序列化为对象
		return deserialize(array);
	}

	@Override
	public Object removeObject(Object key) {
		return deserialize((byte[])cache.removeObject(key));
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
		Cache cache=new SerializedCache(new PerpetualCache());
		cache.putObject("A", 500);
		Object a1=cache.getObject("A");
		Object a2=cache.getObject("A");
		System.out.println(a1==a2);//false
	}

}
