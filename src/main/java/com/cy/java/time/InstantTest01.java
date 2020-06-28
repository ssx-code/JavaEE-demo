package com.cy.java.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

public class InstantTest01 {
	public static void main(String[] args) {
		Instant instant=Instant.now();
		System.out.println(instant);
		
		Set<String> zoneIds=
		ZoneId.getAvailableZoneIds();
		System.out.println(zoneIds);
		
		Clock c=Clock.systemDefaultZone();
		instant=c.instant();
		System.out.println(instant);
		
		
	}
}
