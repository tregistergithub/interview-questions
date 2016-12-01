package com.tal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReverseStringTest {

	@Test
	public void testReverseStringInPlace() {
		String s = "Shula bula, xyz3 muki";
		char[] charArray = s.toCharArray();
		ReverseString.reverseStrInPlaceWithRange(charArray, 12, 15);
		System.out.println(s + " <--> " + String.valueOf(charArray));
		assertEquals(String.valueOf(charArray), "Shula bula, 3zyx muki");
	}

}
