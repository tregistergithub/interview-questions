package com.tbla.tbz.calc;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

import com.tbla.tbz.calc.memory.HashMapMemory;
import com.tbla.tbz.calc.memory.Memory;

// Unary Minus
// Double / Float
// MAX_INTEGER / MIN_INTEGER issues

public class CalculatorTest {

	@Test
	public void testPostIncrementDecrementWithAssignmentOperator() {
		Memory memory = new HashMapMemory();
		Calculator calc = new Calculator(memory);

		int i = 9;
		int j = 3 + i-- - 3 + 3;
		i += 9;
		j += i++;
		j += j++;

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
		
		  int foo = 10 + 1+2*6 + 8/2 ; 
		foo -= foo + 2 * foo / 2 + 2	 ; 
			int mimi	=	11 + 		9	* 2+20/3; 
		mimi-=		29	+ 3 * 3;
		 		foo	-= mimi + 3/3*3/2+3    /	3 + 3*3+3*3+3*2;
		

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
		
		  int foo = 10 +1+ 1+2*6 + 8/2 ; 
		foo *= foo + 2 * foo / 2 + 2	 ; 
			int mimi	=	11 + 		9	* 2+20/3; 
		mimi*=		29	+ 3 * 3;
		 		foo	*= mimi + 3/3*3/2+3    /	3 + 3*3+3*3+3*2;
		

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
		
		  int foo = 10 +1+ 1+2*6 + 8/2 ; 
		foo /= 3+2	 ; 
			int mimi	=	11 + 		9	* 2+20/3; 
		mimi /=		2	- 3 ;
		 		foo	/= mimi + 34;
		

		Stream.of("  foo =  10 +1+ 1+2*6 + 8/2 ", 
				"foo /= 3+2	 ", 
				"	mimi	=	11 + 		9	* 2+20/3", 
				"mimi /=		2	- 3 ", 
				" 		foo	/= mimi + 34			")
				.forEach(strStmt -> calc.evalStmtStr(strStmt));

		assertEquals("memory", "(foo=-5,mimi=-35)", memory.toString());
	}
}
