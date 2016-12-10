package com.tbla.tbz.calc;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.tbla.tbz.calc.memory.HashMapMemory;
import com.tbla.tbz.calc.memory.Memory;
import com.tbla.tbz.calc.parser.StatementException;
import com.tbla.tbz.calc.parser.Token;
import com.tbla.tbz.calc.parser.TokenType;
import com.tbla.tbz.calc.parser.TokenUtil;
import com.tbla.tbz.calc.parser.Token.EvalAction;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

// Unary Minus
// Double / Float
// MAX_INTEGER / MIN_INTEGER issues
// Excessive Plus like 1 + + 1 - won't work

public class CalculatorTest {

	@Test
	public void testPostIncrementDecrementWithAssignmentOperator() {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of(
				"i =9", 
				"j = 3+ i-- - 3 +3", 
				"i +=9", 
				"j += i++", 
				"j += j++"

		).forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory",  "(i=18,j=58)", memory.toString());
	}

	@Test
	public void testAssingment() {

		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of("  foo = 1", 
				"foo = foo ", 
				"mimi=11", 
				"joe=		foo	   ", 
				" 		bar	= 3")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory",  "(bar=3,foo=1,joe=1,mimi=11)", memory.toString());
	}

	@Test
	public void testPlus() {

		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of("  foo = 1+1+1+1+1", 
				"foo = foo + foo + 1 + 22+     foo", 
				"	mimi	=	11 + 		11	", 
				"joe=		foo	   +9", 
				" 		bar	= 3+3")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory",  "(bar=6,foo=38,joe=47,mimi=22)", memory.toString());
	}
	
	@Test
	public void testMinus() {

		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of("  foo = 10-1-1-1-3", 
				"foo = foo - foo - 1 - 22-     foo", 
				"	mimi	=	11 - 		9	", 
				"joe=		foo	   -9", 
				" 		bar	= 3-3")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(bar=0,foo=-27,joe=-36,mimi=2)", memory.toString());
	}

	@Test
	public void testMul() {

		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of("  foo = 10*1*1*1*3", 
				"foo = foo * foo * 1 * 22*     foo", 
				"	mimi	=	11 * 		9	", 
				"joe=		foo	   *9", 
				" 		bar	= 3*3")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(bar=9,foo=594000,joe=5346000,mimi=99)",  memory.toString());
	}

	@Test
	public void testDiv() {

		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of("  foo = 10/1/1/1/3			", 
				"foo = foo / foo	 ", 
				"	mimi	=	11 / 		9	", 
				"joe=		29	/ 3", 
				" 		bar	= 3/3")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(bar=1,foo=1,joe=9,mimi=1)", memory.toString());
	}

	@Test
	public void testOperatorPrecendence() {

		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of("  foo = 10 + 1+2*6 + 8/2 ", 
				"foo = foo + 2 * foo / 2 + 2	 ", 
				"	mimi	=	11 + 		9	* 2+20/3", 
				"joe=		29	+ 3 * 3", 
				" 		bar	= 3/3*3/2+3    /	3 + 3*3+3*3+3*2")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(bar=26,foo=56,joe=38,mimi=35)", memory.toString());
	}

	@Test
	public void testAssignmentOperatorPlus() {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		Stream.of("  foo = 10 + 1+2*6 + 8/2 ", 
				"foo += foo + 2 * foo / 2 + 2	 ", 
				"	mimi	=	11 + 		9	* 2+20/3", 
				"mimi+=		29	+ 3 * 3", 
				" 		foo	+= mimi + 3/3*3/2+3    /	3 + 3*3+3*3+3*2")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(foo=182,mimi=73)", memory.toString());
	}

//    ScriptEngineManager engineManager = 
//	new ScriptEngineManager();
//	    ScriptEngine engine = 
//	engineManager.getEngineByName("nashorn");
//	    //\"(foo=\" + foo +\",sami=\" + sami + \")\";
//	    Object eval = engine.eval("foo = 10 + 1+2*6 + 8/2; foo += foo + 2 * foo / 2 + 2; mimi	=	11 + 		9	* 2+20/3; mimi+=		29	+ 3 * 3; foo	+= mimi + 3/3*3/2+3    /	3 + 3*3+3*3+3*2;  Math.floor(foo); ");
//	    System.out.println(eval);

	@Test
	public void testAssignmentOperatorMinus() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		Stream.of("  foo = 10 + 1+2*6 + 8/2 ", 
				"foo -= foo + 2 * foo / 2 + 2	 ", 
				"	mimi	=	11 + 		9	* 2+20/3", 
				"mimi-=		29	+ 3 * 3", 
				" 		foo	-= mimi + 3/3*3/2+3    /	3 + 3*3+3*3+3*2")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(foo=-52,mimi=-3)", memory.toString());
	}
	
	@Test
	public void testAssignmentOperatorMul() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of("  foo = 10 +1+ 1+2*6 + 8/2 ", 
				"foo *= foo + 2 * foo / 2 + 2	 ", 
				"	mimi	=	11 + 		9	* 2+20/3", 
				"mimi*=		29	+ 3 * 3", 
				" 		foo	*= mimi + 3/3*3/2+3    /	3 + 3*3+3*3+3*2")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(foo=2202144,mimi=1330)", memory.toString());
	}
	
	@Test
	public void testAssignmentOperatorDiv() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		Stream.of("  foo =  10 +1+ 1+2*6 + 8/2 ", 
				"foo /= 3+2	 ", 
				"	mimi	=	11 + 		9	* 2+20/3", 
				"mimi /=		2	- 3 ", 
				" 		foo	/= mimi + 34			")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(foo=-5,mimi=-35)", memory.toString());
	}
	
	@Test
	public void testPreIncrement() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of("  foo =  10 +1+ 1+2*6 + 8/2 ", 
				"foo += 3+2 + ++foo + ++   foo	 ", 
				"	mimi	=	11 + ++foo +		9	* 2+20/3", 
				"mimi =		2	- 3 * ++mimi", 
				" 		foo	*= ++mimi + ++  mimi + ++foo + 34			")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(foo=-59427,mimi=-383)", memory.toString());
	}
	
	@Test
	public void testPostIncrement() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
	
		Stream.of("  foo =  10 +1+ 1+2*6 + 8/2 ", 
				"foo += 3+2 + foo++ +    foo	 ++", 
				"	mimi	=	11 + foo++ +		9	* 2+20/3", 
				"mimi =		2	- 3 * mimi++", 
				" 		foo	*= mimi++ +   mimi++ + foo++ + 34			")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(foo=-56420,mimi=-371)", memory.toString());
	}

	@Test
	public void testPostDecrement() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		Stream.of("  kip_i2 =  2*2/2+2*2/2 /2+2-2*2/2 *2 ",
				"li112_s  =  --kip_i2*3 -1 ",
				" li112_s += 3+2 + kip_i2-- +    kip_i2	 --", 
				"	d2	=	11 + kip_i2-- +		9 -li112_s--	* 2+20/3", 
				"d2 =		2	- 3 * d2--", 
				" 		kip_i2	*= d2-- +   li112_s-- + li112_s-- + 34			")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(d2=-53,kip_i2=45,li112_s=0)", memory.toString());
	}

	@Test
	public void testPreDecrement() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		Stream.of(" kip_i2 =  2*2/2+2*2/2 /2+2-2*2/2 *2  ",
				" li112_s  =  --kip_i2*3 -1  ",
				" li112_s += 3+2 + --kip_i2 +    --kip_i2	", 
				"	d2	=	11 + --kip_i2 +		9 - --li112_s	* 2+20/3", 
				"d2 =		2	- 3 * --d2", 
				" 		kip_i2	*= d2-- +   --li112_s + --li112_s + 34	")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(d2=-65,kip_i2=99,li112_s=-2)", memory.toString());
	}

	@Test
	public void testParenthesis() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		Stream.of(" kip_i2 =  2*(2/2+2)*(2/2 /(2+2))+(2-2)*2/2 *2  ",
				" li112_s  =  --kip_i2*(3 -1)  ",
				" li112_s += 3*(2 + (--kip_i2 +    --kip_i2))	", 
				"	d2	=	(11 + (--kip_i2)) *((		9 - --li112_s)	* (2+20)/3)", 
				"d2 =		((2	- 3) * (--d2))", 
				" 		kip_i2	*= (((d2--) +   (--li112_s))) * (((--li112_s)) + 34)	")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(d2=-1078,kip_i2=87200,li112_s=-14)", memory.toString());
	}

	@Test
	public void testMixes() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		Stream.of(" kip_i2 =  2*(2/2+2)*(2/2 /(2+2))+(2-2)*2/2 *2  ",
				" li112_s  =  --kip_i2*(3 -1)  ",
				" li112_s += 3*(2 + (kip_i2-- +    --kip_i2))	", 
				"	d2	=	(11 + (--kip_i2)) *((		9 - li112_s++)	* (2+20)/3)", 
				"d2 *=		((2	- 3) * (++d2))", 
				"	d2	/=	100 / (2+2) * 3", 
				" 		kip_i2	*= (((d2++) +   (li112_s++))) * (((++li112_s)) + 34)	")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(d2=-10056,kip_i2=1167424,li112_s=-5)", memory.toString());
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testNegative_ParsingErrors() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		// [a-zA-Z][a-zA-Z0-9_]{0,29}$

		// Test variable length
		try {
			Stream.of("	L234567890_234567890_234567890a = 2 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(7)));
		}

		// Test variable invalid char
		try {
			Stream.of("	L2@ = 2 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(7)));
		}
		
		// Test variable starts with a number
		try {
			Stream.of("	2 = 2 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(7)));
		}
	
	}

	@Test
	public void testNegative_UndefinedVariable() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		// Test undefined variable in assignment operator 
		try {
			Stream.of("	i += 2 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(11)));
		}
		
		// Test undefined variable in numeric expression 
		try {
			Stream.of("	i = i ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(3)));
		}

		// Test undefined variable in unary increment 
		try {
			Stream.of("	j = ++i ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(3)));
		}
	}
	
	@Test
	public void testNegative_ParenthesisParsing() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		// Missing left parenthesis
		try {
			Stream.of("	i = (2)) ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(13)));
		}
		
		// Missing left parenthesis
		try {
			Stream.of("	i = 2)) ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(13)));
		}

		// Missing left parenthesis
		try {
			Stream.of("	i  = (2) / (1+2)) ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(13)));
		}

		// Missing right parenthesis
		try {
			Stream.of("	i = (1 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(5)));
		}
		
		// Missing right parenthesis
		try {
			Stream.of("	i = (1) + (2 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(5)));
		}
	}
	
	@Test
	public void testNegative_UnsopprtedBinaryOperator() throws Exception {
		
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		// Test undefined variable in assignment operator 
		try {
			Stream.of("	i += 2 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(11)));
		}
		
		// Test undefined variable in numeric expression 
		try {
			Stream.of("	i = i ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(3)));
		}
	}
	
	@Test
	public void testNegative_MissingMathException() throws Exception {
		
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		// Missing math expression before the end of the line 
		try {
			Stream.of("	i =  ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(8)));
		}
		
		
		// Missing math expression at the end of the line 
		try {
			Stream.of("	i =").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(10)));
		}
	}
	
	@Test
	public void testNegative_UnexpectedCharInMathException() throws Exception {
		
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		try {
			Stream.of("	i =  @").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(9)));
		}
	}
	
	@Test
	public void testNegative_MissingAssignmentOperator() throws Exception {
		
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		// Missing assignment sign
		try {
			Stream.of("	i   1").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(8)));
		}

		// Missing variable name
		try {
			Stream.of("	=").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch(StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(7)));
		}

	}

	@Test
	public void testNegative_MissingOperands() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
	    thrown.expect( StatementException.class );
	    thrown.expectMessage(StatementException.makeErrCode(2));
		Stream.of("	i  = 3+").forEach(strStmt -> calc.evalStmtStr(strStmt));
	}

	
	@Test
	public void testNegative_MissingOperator() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
	    thrown.expect( StatementException.class );
	    thrown.expectMessage(StatementException.makeErrCode(12));
		Stream.of("	i  = 3 2").forEach(strStmt -> calc.evalStmtStr(strStmt));
	}

	@Test
	public void testNegative_UnsupportedTokenDuringInfixToPostfixConversion() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		// Test unsupported binary operator

		MockUp<TokenUtil> tokenUtilMock = null;
		try {
			tokenUtilMock = new MockUp<TokenUtil>() {
				@Mock
				private EvalAction getEvaluationAction(Invocation invocation, TokenType tokenType, EvalAction incAction, EvalAction decAction) {
					return invocation.proceed(TokenType.LEFT_PAREN, incAction, decAction);
				}
			};

			Stream.of("	i = 0 ", "	j = ++i ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch (IllegalArgumentException se) { // Do 
		} finally {
			tokenUtilMock.tearDown();
		}

	}
	
	@Test
	public void testNegative_UnsupportedTokenDuringEvaluation() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		// Test unsupported binary operator

		MockUp<Calculator> calculatorMock = null;
		try {
			calculatorMock = new MockUp<Calculator>() {
				@Mock
				private int getValueAndEval(Invocation invocation, Token token) {
					return invocation.proceed(new Token(TokenType.LEFT_PAREN.asString(), TokenType.LEFT_PAREN));
				}
			};

			Stream.of("	j = 1+1 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch (StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(4)));
		} finally {
			calculatorMock.tearDown();
		}

	}
	
	@Test 
	public void testNegative_UnsupportedBinaryOperator() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		// Test unsupported binary operator

		MockUp<Calculator> calculatorMock = null;
		try {
			calculatorMock = new MockUp<Calculator>() {
				@Mock
				private Token evalBinaryExpression(Invocation invocation, int int1, int int2, TokenType tokenType) {
					return invocation.proceed(int1, int2, TokenType.LEFT_PAREN);
				}
			};

			Stream.of("	j = 1+1 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
			fail("Should not reach this point");
		} catch (StatementException se) {
			assertTrue(se.getMessage(), se.getMessage().contains(StatementException.makeErrCode(1)));
		} finally {
			calculatorMock.tearDown();
		}
	}

	
	@Test
	public void testNegativeEndCases() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);
		
		// [a-zA-Z][a-zA-Z0-9_]{0,29}$

	    thrown.expect( StatementException.class );
	    thrown.expectMessage(StatementException.makeErrCode(7));
		Stream.of("	L234567890_234567890_234567890a = 2 ").forEach(strStmt -> calc.evalStmtStr(strStmt));
	}

	@Ignore("Not implemented yet")
	public void testMemoryLeaks() throws Exception {
		fail("not implemented");
	}
	
	@Test
	public void testIdentifyVariableTokens() throws Exception {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		// [a-zA-Z][a-zA-Z0-9_]{29}$

		Stream.of(" a =  1  ",
				" ab  =  a  ",
				" Ab  =  a + 2  ",
				" aB  =  a + 4  ",
				" AB  =  a + 6  ",
				" i1 = ab	", 
				" i_____ = i1	", 
				" i_1_2_0_2_3_4_6_7_8_9_4_ = i1	", 
				" L234567890_234567890_234567890 = 		i_1_2_0_2_3_4_6_7_8_9_4_	",
				"a2_2 =	 aB + AB",
				"	a2_2 +=	 a "
				).forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(AB=7,Ab=3,L234567890_234567890_234567890=1,a=1,a2_2=13,aB=5,ab=1,i1=1,i_1_2_0_2_3_4_6_7_8_9_4_=1,i_____=1)", memory.toString());
	}
}