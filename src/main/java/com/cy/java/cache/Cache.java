package com.cy.java.cache;
/**
 * 缓存产品标准设计
 * @author qilei
 *
 */
public interface Cache {
	/**向缓存容器放数据*/
	public void putObject(Object key,Object value);
	/**从缓存中获取数据*/
	public Object getObject(Object key);
	/**从缓存中移除数据*/
	public Object removeObject(Object key);
	/**清除缓存数据*/
	public void clear();
	/**返回缓存中有效元素个数*/
	public int size();
}
