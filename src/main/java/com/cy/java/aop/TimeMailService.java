package com.cy.java.aop;
public class TimeMailService extends MailService {
	@Override
	void sendMsg(String msg) {
		long t1=System.nanoTime();
		super.sendMsg(msg);
		long t2=System.nanoTime();
		System.out.println("执行时长:"+(t2-t1));
	}
}
//CGLIB Proxy