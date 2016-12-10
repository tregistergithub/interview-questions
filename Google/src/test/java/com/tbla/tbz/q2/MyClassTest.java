package com.tbla.tbz.q2;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class MyClassTest {

	@Test
	public void test() {
		
		List<Long> numbers = Arrays.asList(new Long[] {1l,2l,30000l});
		List<String> strings = Arrays.asList(new String[] {"aaa","nnn"});
		
		Date time = new Date(new Date().getTime()+10L);
		MyClass c = new MyClass(time, "tal", numbers, strings);
		System.out.println(c.isHistoric() ? "historic" : "future");
		//c.removeString("aaa");
		new Long(30000);
		System.out.println(c.containsNumber(30000) ? "contains number");
		System.out.println(c);
	}
}
