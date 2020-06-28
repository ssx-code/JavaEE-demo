package com.cy.java.aop;

public final class DefaultSearchService implements SearchService {

	@Override
	public Object seach(String key) {
	    System.out.println("seach by "+key);
		return null;
	}

}
