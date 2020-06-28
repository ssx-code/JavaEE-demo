package com.cy.java.cache;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * 构建线程安全的cache,基于synchronized关键字构建线程安全方法。
 * 优势：在多线程并发环境可以保证map中数据的正确性。
 * 劣势：性能和并发会降低。
 * 思考：你觉得这种劣势有改进的地方吗？
 * 
 */
public class SynchronizedCache implements Cache {

	private Cache cache;
	public SynchronizedCache(Cache cache) {
		this.cache=cache;
	}
	/**
	 * synchronized 是一种排他锁(独占锁)，一旦有一个线程进入了synchronized方法，其它线程只能阻塞。
	 */
	@Override
	public synchronized void putObject(Object key, Object value) {
		cache.putObject(key, value);
	}

	@Override
	public synchronized Object getObject(Object key) {
		// TODO Auto-generated method stub
		return cache.getObject(key);
	}

	@Override
	public synchronized Object removeObject(Object key) {
		// TODO Auto-generated method stub
		return cache.removeObject(key);
	}

	@Override
	public synchronized void clear() {
		cache.clear();
	}
	@Override
	public synchronized int size() {
		return cache.size();
	}
	@Override
	public String toString() {
		return cache.toString();
	}
	
    public static void main(String[] args) {
    	
    	//装饰模式，油漆工模式
		Cache cache=new SynchronizedCache(new PerpetualCache());
		cache.putObject("A", 100);
		cache.putObject("B", 200);
		cache.putObject("C", 300);
		cache.putObject("D", 400);
		System.out.println(cache);
		cache.removeObject("C");
		System.out.println(cache);
		
	}
	
	
}
