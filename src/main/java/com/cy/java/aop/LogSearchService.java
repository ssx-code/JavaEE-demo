package com.cy.java.aop;

public class LogSearchService implements SearchService {

	//has a 
	private DefaultSearchService searchService;
	public LogSearchService(DefaultSearchService searchService) {
		this.searchService=searchService;
	}
	@Override
	public Object seach(String key) {
		System.out.println("start:"+System.nanoTime());
		Object result=searchService.seach(key);
		System.out.println("end:"+System.nanoTime());
		return result;
	}

}
