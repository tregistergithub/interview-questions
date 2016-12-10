package com.exam.tbz.q1.parser;

import java.util.Arrays;
import java.util.Queue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StatementParserTest {

	@Test
	public void testStatmentParsing() {
		StatementParser sp = new StatementParser("i += 1 + 2 * 3");
		Token assignmentTokenType = sp.getAssignmentTokenType();
		Queue<Token> generatePostfixTokenQueue = sp.generatePostfixTokenQueue();
		Object[] posixAsArr = generatePostfixTokenQueue.toArray();
		assertEquals("assignment token", "+", assignmentTokenType.getTokenStr());
		assertEquals("posix queue", "[1, 2, 3, *, +]", Arrays.toString(posixAsArr));
		assertEquals("variable name", "i", sp.getVariableName());
	}

}
