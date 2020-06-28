package com.cy.java.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftCache implements Cache {
	
	private Cache cache;
	/**基于此队列记录被垃圾回收的对象：当软引用引用的对象被GC时，会将被GC的对象的软引用存储到ReferenceQueue中*/
	private ReferenceQueue<Object> garbageOfReferenceQueue;
    public SoftCache(Cache cache) {
    	this.cache=cache;
    	this.garbageOfReferenceQueue=new ReferenceQueue<>();
	}
    /**
     * 构建软引用类型
     * @author qilei
     */
	private static class SoftEntry extends SoftReference<Object>{
        private final Object key;
        /**
         * @param key
         * @param referent 被弱引用引用的对象，此对象会对应cache中的value
         * @param 引用队列
         */
		public SoftEntry(Object key,Object referent, ReferenceQueue<? super Object> rQueue) {
			super(referent, rQueue);
			this.key=key;
		}
	}
	/**从队列中取出已经被gc对象，然后从cache中移除*/
	private  void removeGarbageObjects() {
		SoftEntry softEntry=null;
		while((softEntry=(SoftEntry)garbageOfReferenceQueue.poll())!=null) {
			cache.removeObject(softEntry.key);
		}
	}
	@Override
	public void putObject(Object key, Object value) {
		//1.移除garbageOfReferenceQueue队列中记录的对象
		removeGarbageObjects();
		//2.对对象进行软引用然后存储到cache中。
		cache.putObject(key, new SoftEntry(key, value, garbageOfReferenceQueue));
	}

	/**
	 * 基于key获取cache中的SoftReference引用的对象
	 */
	@Override
	public Object getObject(Object key) {
		//1.从cache获取引用对象并校验
		SoftEntry softEntry=(SoftEntry)cache.getObject(key);
		if(softEntry==null)cache.removeObject(key);
		//2.基于引用获取引用的对象并校验
		Object target=softEntry.get();
		if(target==null)cache.removeObject(key);
		return target;
	}

	@Override
	public Object removeObject(Object key) {
	    //1.移除垃圾对象
		removeGarbageObjects();
		//2.移除目标引用对象
		Object target=cache.removeObject(key);
		return target;
	}

	@Override
	public void clear() {
		removeGarbageObjects();
		cache.clear();
	}

	@Override
	public int size() {
		removeGarbageObjects();
		return cache.size();
	}
	@Override
	public String toString() {
		return cache.toString();
	}
	/**
	 * 测试：在内存不足和内存充足的情况下cache中的数据变化
	 * @param args
	 */
	//-Xmx5m -Xms5m -XX:+PrintGCDetails
	public static void main(String[] args) {
		Cache cache=new SoftCache(new PerpetualCache());
		cache.putObject("A", new byte[1024*1024]);
		cache.putObject("B", new byte[1024*1024]);
		cache.putObject("C", new byte[1024*1024]);
		cache.putObject("D", new byte[1024*1024]);
		cache.putObject("E", new byte[1024*1024]);
		cache.putObject("F", new byte[1024*1024]);
		cache.putObject("K", new byte[1024*1024]);
		System.out.println(cache.size());
		System.out.println(cache);
	}

}
