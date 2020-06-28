package com.cy.java.debug;
//F8当前断点运行结束，进入下一个断点，没有下一个断点了则直接结束
//F6单步调试
//F5进入要调用的方法内部
//F7从方法内部出来
public class TestDebug01 {
	static void doMethod02() {
		System.out.println("doMethod02");
	}
	static void doMethod01() {
		System.out.println("doMethod01");
		doMethod02();
	}
	public static void main(String[] args) {
		System.out.println("main");
		doMethod01();
		
		Integer a=100;//Integer.valueOf(100)
	}
}
