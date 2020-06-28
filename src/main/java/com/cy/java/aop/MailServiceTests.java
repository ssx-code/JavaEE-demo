package com.cy.java.aop;

public class MailServiceTests {

	public static void main(String[] args) {
		MailService mailService=new TimeMailService();
		mailService.sendMsg("hello cgb2002");
	}
}
