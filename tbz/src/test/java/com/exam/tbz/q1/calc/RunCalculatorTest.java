package com.exam.tbz.q1.calc;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RunCalculatorTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanupStreams() {
		System.setOut(null);
		System.setIn(null);
	}

	@Test
	public void test() throws Exception {
		ByteArrayInputStream inContent = new ByteArrayInputStream(
				("i = 0\n" + 
				"j = ++i\n" + 
				"x = i++ + 5\n" + 
				"y = 5 + 3 * 10\n" + 
				"i += y\n" + "\n").getBytes());

		System.setIn(inContent);
		RunCalculator.main(null);
		assertTrue("stdout memory",  outContent.toString().contains("(i=37,j=1,x=6,y=35)"));
	}

}
