package com.tbla.tbz.q2;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class MyClassTest {

	@Test
	public void testFunctionaly() {
		
		List<Long> numbers = Arrays.asList(new Long[] {1l,2l,30000l});
		List<String> strings = Arrays.asList(new String[] {"aaa","nnn"});
		
		Date time = new Date(new Date().getTime()-10L);
		MyClassFixed c = new MyClassFixed(time, "tal", numbers, strings);
		System.out.println(c.isHistoric() ? "historic" : "future");
//		c.removeString("aaa");
		System.out.println(c.containsNumber(30000) ? "contains number" : "not there");
		System.out.println(c);		
	}
}
