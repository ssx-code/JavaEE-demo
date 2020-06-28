package com.cy.java.thread;

public class TestThread01 {

	public static void main(String[] args) {
		Thread t1=new Thread() {
			@Override
			public void run() {//callback
				System.out.println("run");
			}
		};
		t1.start();//就绪，可以获取CPU，
	}
}
