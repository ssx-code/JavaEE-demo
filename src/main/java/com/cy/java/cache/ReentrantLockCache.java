package com.cy.java.cache;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 构建线程安全对象，基于ReentrantReadWriteLock对象实现读写锁应用。
 * @author qilei
 */
public class ReentrantLockCache implements Cache {

	private Cache cache;
	/**
	 * 此对象提供了读锁，写锁应用方式.
	 * 1)写锁：排他锁
	 * 2)读锁：共享锁
	 * 说明：读写不能同时执行。
	 */
	private final ReentrantReadWriteLock readWriteLock = 
			new ReentrantReadWriteLock();
	public ReentrantLockCache(Cache cache) {
		this.cache=cache;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void putObject(Object key, Object value) {
		readWriteLock.writeLock().lock();
		try {
		cache.putObject(key, value);
		}finally {
		readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public Object getObject(Object key) {
		readWriteLock.readLock().lock();
		try {
	    Object object=cache.getObject(key);
	    return object;
		}finally{
			readWriteLock.readLock().unlock();
		}
	}

	@Override
	public Object removeObject(Object key) {
		readWriteLock.writeLock().lock();
		try {
	    Object object=cache.removeObject(key);
	    return object;
		}finally{
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public void clear() {
		readWriteLock.writeLock().lock();
		try {
	      cache.clear();
		}finally{
		readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public int size() {
		readWriteLock.readLock().lock();
		try {
	    int size=cache.size();
	    return size;
		}finally{
			readWriteLock.readLock().unlock();
		}
	}

}
