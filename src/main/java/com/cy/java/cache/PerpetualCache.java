package com.cy.java.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 永久Cache:没有设置缓存淘汰策略。
 * 1)可能会出现内存溢出(OutOfMemoryError)：不可存储大量数据
 * 2)可能会存在线程安全:不可以多线程共享操作。
 */
public class PerpetualCache implements Cache {

	/**
	 * JDK中的hashMap存在线程不安全问题:
	 * 1)JDK7之前坑内会出现死循环(环形链)
	 * 2)JDK8之后会出现值覆盖的现象。
	 */
	private Map<Object,Object> cache=new HashMap<>();
	@Override
	public void putObject(Object key, Object value) {
		cache.put(key, value);
	}

	@Override
	public Object getObject(Object key) {
		return cache.get(key);
	}

	@Override
	public Object removeObject(Object key) {
		return cache.remove(key);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public int size() {
		return cache.size();
	}
	@Override
	public String toString() {
		return cache.toString();
	}
	//jvm 参数
	//最大堆：-Xmx5m 
	//最小堆：-Xms5m
	//应用 -Xmx5m -Xms5m -XX:+PrintGCDetails
	public static void main(String[] args) {
		Cache cache=new PerpetualCache();
		cache.putObject("A", 100);
		cache.putObject("B", 200);
		cache.putObject("C", 300);
		cache.putObject("D", 400);
		cache.putObject("E", new byte[1024*1024]);
		cache.putObject("F", new byte[1024*1024]);
		cache.putObject("G", new byte[1024*1024]);
		cache.putObject("J", new byte[1024*1024]);
		System.out.println(cache);
		System.out.println(cache.size());
		cache.removeObject("D");
		System.out.println(cache);
		cache.clear();
		System.out.println(cache.size());

	}

}





