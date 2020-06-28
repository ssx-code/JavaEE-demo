package com.cy.java.cache;

import java.util.LinkedHashMap;
/**
 * 基于LRU算法实现LRUCache，在缓存满的时候，淘汰最近最少访问的一些对象。
 * LRU算法：缓存淘汰算法，一种最近最少实用算法。
 */
public class LruCache implements Cache {
	/**负责存储数据的目标Cache*/
	private Cache cache;
	/**记录最近最少访问的对象key*/
	private Object eldestKey;
	/**定义一个对象，用于记录最近缓存中对象的访问次数*/
	//LinkedHashMap在HashMap的基础上添加了一个双向链表，基于此链表记录元素的添加顺序(默认)或访问顺序
	private LinkedHashMap<Object, Object> keyMap;
	
	@SuppressWarnings("serial")
	public LruCache(Cache cache,int cap) {//cap代表最大容量
		this.cache=cache;
		this.keyMap=new LinkedHashMap<Object, Object>((int)(cap/0.75)+1,0.75f,true) {//true表示记录访问顺序
			/**此方法在每次执行put操作时都会执行*/
			@Override
			protected boolean removeEldestEntry(java.util.Map.Entry<Object, Object> eldest) {
				boolean isFull=size()>cap;
				if (isFull) {
					LruCache.this.eldestKey=eldest.getKey();
				}
				return isFull;//true时会自动从keyMap中移除key
			}
		};
	}
	
	@Override
	public void putObject(Object key, Object value) {
	    //存储对象
		cache.putObject(key, value);
		//记录key的访问顺序
		keyMap.put(key, key);
		//移除最近最少访问的元素
		if(eldestKey!=null) {
			cache.removeObject(eldestKey);
			eldestKey=null;
		}
	}

	@Override
	public Object getObject(Object key) {
	    //记录key的访问顺序
		keyMap.get(key);
		//直接基于key获取元素并返回
		return cache.getObject(key);
	}
	@Override
	public Object removeObject(Object key) {
		Object obj=cache.removeObject(key);
		keyMap.remove(key);
		return obj;
	}
	@Override
	public void clear() {
	    cache.clear();
	    keyMap.clear();
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
		Cache cache=new SynchronizedCache(new LruCache(new PerpetualCache(), 3));
		cache.putObject("A", 100);
		cache.putObject("B", 200);
		cache.putObject("C", 300);
		cache.putObject("D", 400);
		System.out.println(cache);//B,C,D
		cache.putObject("E", 500);//C,D,E
		cache.getObject("C");
		cache.putObject("F", 600);
		System.out.println(cache);//E,C,F
	}

}








