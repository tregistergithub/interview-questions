package com.exam.tbz.q1.memory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class HashMapMemoryTest {

	@Test
	public void testToString() {
		Memory m = new HashMapMemory();
	}
	
	@Test
	public void testMemoryBasicFunctionality() {
		Memory m = new HashMapMemory();
		m.set("a", 1);
		Integer aVal = m.get("a");
		assertEquals("set & get var", aVal, Integer.valueOf(1));
		assertNull("get missing var", m.get("non existant var"));
	}
		
	@Test
	public void testNegative_MemoryBasicFunctionality() {
		Memory m = new HashMapMemory();
		// Negative flow - null variable name
		try {
			m.set(null, 1);
			fail("Should not reach here");
		} catch (Exception e) {
			// Do nothing
		} 
		
		// Negative flow - null value
		try {
			m.set("a", null);
			fail("Should not reach here");
		} catch (Exception e) {
			// Do nothing
		} 
	}
}
