package com.tbla.tbz.calc;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import org.junit.Test;

import com.tbla.tbz.calc.memory.HashMapMemory;
import com.tbla.tbz.calc.memory.Memory;

public class CalculateQuesion1ExampleTest {

	@Test
	public void testQuestion1() {
		Memory memory = new HashMapMemory();
		Calculator calculator = new Calculator(memory);
		
		Stream.of(
				"i = 0",
				"j = ++i",
				"x = i++ + 5",
				"y = 5 + 3 * 10",
				"i += y"
				
				).forEach(strStmt-> calculator.evaluate(strStmt));
		
		assertEquals("memory", "(i=37,j=1,x=6,y=35)", memory.toString());
	}

}
