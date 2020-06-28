package com.cy.java.cache;
/**
 * 为目标cache对象添加日志记录功能，输出命中率
 * @author qilei
 */
public class LoggingCache implements Cache{
	private Cache cache;
	/**表示请求次数*/
	private int requests;
	/**命中次数(命中表示从缓存中取到数据了)*/
	private int hits;
	public LoggingCache(Cache cache) {
		this.cache=cache;
	}
	@Override
	public void putObject(Object key, Object value) {
		cache.putObject(key, value);
	}
	@Override
	public Object getObject(Object key) {
		requests++;
	    Object obj=cache.getObject(key);
	    if(obj!=null)hits++;
	    //记录日志耗时
	    System.out.println("Cache hit Ratio:"+hits*1.0/requests);
		return obj;
	}
	@Override
	public Object removeObject(Object key) {
		return   cache.removeObject(key);
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
		Cache cache= new SynchronizedCache(new LoggingCache(new PerpetualCache()));
		cache.putObject("A", 100);
		cache.putObject("B", 200);
		cache.putObject("C", 300);
		cache.putObject("D", 400);
		System.out.println(cache);
		cache.getObject("E");
		cache.getObject("A");
		cache.getObject("B");
	}

}
