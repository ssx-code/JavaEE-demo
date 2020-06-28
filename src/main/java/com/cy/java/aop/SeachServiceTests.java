package com.cy.java.aop;

public class SeachServiceTests {

	public static void main(String[] args) {
		SearchService service=new LogSearchService(new DefaultSearchService());
		service.seach("tedu");
	}
}
