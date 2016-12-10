package com.exam.tbz.q2;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class MyClassFixedTest {
	@Test
	public void testFunctionaly() {
		List<Long> numbers = Arrays.asList(new Long[] {1l,2l,30000l});
		List<String> strings = Arrays.asList(new String[] {"aaa","nnn"});
		MyClassFixed c = new MyClassFixed(new Date(0L), "tal", numbers, strings);
		c.removeString("aaa"); 
		assertTrue(c.isHistoric());
		assertTrue(c.containsNumber(30000));
	}
}
