package com.cy.java.debug;

import java.util.ArrayList;
import java.util.List;

public class TestDebug02 {
	static int doMethod01() {
		int a=10;
		try {
		a++;
		return a;//return result=11
		}finally {
		a++;
		}
	}
	static int doMethod02() {
		
		int a=10;
		try {
			a++;
		}finally {
			a++;
		}
		return a;//return result=12
	}
	public static void main(String[] args) {
		int num=doMethod01();
		System.out.println(num);//11
		List<String> list=new ArrayList<>(),aa=list;
		System.out.println(aa);
	}
}
