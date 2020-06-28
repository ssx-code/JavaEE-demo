package com.cy.java.time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeTests {

	public static void main(String[] args) {
		LocalDateTime time=LocalDateTime.now();
		System.out.println(time);
		int hour=time.getHour();
		System.out.println(hour);
		long t1=time.toEpochSecond(ZoneOffset.UTC);
		System.out.println(t1);
		
	}
}
