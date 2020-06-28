package com.cy.java.cache;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 基于FIFO(先进先出)算法，定义FifoCache,当缓存满的时候，从缓存中移除数据
 * @author qilei
 *  
 */
public class FifoCache implements Cache {

	private Cache cache;
	private int maxCap;
	/**通过此队列记录元素的添加顺序*/
	private Deque<Object> keyOrders;
	
	public FifoCache(Cache cache,int maxCap) {
		this.cache=cache;
		this.maxCap=maxCap;
		this.keyOrders=new LinkedList<>();
	}
	/**向cache中放数据：cache满了要淘汰最早放入的元素*/
	@Override
	public void putObject(Object key, Object value) {
		//1.记录key的添加顺序
		this.keyOrders.addLast(key);
		//2.存储元素
		//2.1检查容器是否已满，满了则先移除
		if(keyOrders.size()>maxCap) {
			Object eldestKey=keyOrders.removeFirst();
			cache.removeObject(eldestKey);
		}
		//2.2添加新元素
		cache.putObject(key, value);
	}

	@Override
	public Object getObject(Object key) {
		// TODO Auto-generated method stub
		return cache.getObject(key);
	}

	@Override
	public Object removeObject(Object key) {
		Object object=cache.removeObject(key);
		keyOrders.remove(key);
		return object;
	}

	@Override
	public void clear() {
		cache.clear();
		keyOrders.clear();
	}

	@Override
	public int size() {
		return cache.size();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return cache.toString();
	}
	public static void main(String[] args) {
		Cache cache=new FifoCache(new PerpetualCache(), 3);
		cache.putObject("A", 100);
		cache.putObject("B", 200);
		cache.putObject("C", 300);
		cache.putObject("D", 400);
		System.out.println(cache);//BCD
		cache.getObject("B");
		cache.putObject("E",500);
		System.out.println(cache);//CDE
	}

}





