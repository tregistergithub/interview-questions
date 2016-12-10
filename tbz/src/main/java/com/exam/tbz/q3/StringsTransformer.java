package com.exam.tbz.q3;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Question 3</b>
 * <p/>
 * Jimmy was tasked with writing a class that takes a base list of strings 
 * and a series of transformations and applies them, returning the end result.
 * To better utilize all available resources, the solution was done in a multi-threaded fashion.
 * <p/>
 * Explain the problems with this solution, and offer 2 alternatives. 
 * Discuss the advantages of each approach.
 *
 */
public class StringsTransformer {
	private List<String> data = new ArrayList<String>();

	public StringsTransformer(List<String> startingData) { 
		this.data = startingData;
	}

	// Each thread increases the amount of memory by O(nm) of space and time (m = func count, n=string count)
	// it can be done by O(n) of additional memory and in minimal time of O(n) by writing to a single output
	// The code assumes loops over all the strings and applies a function to it and it assumes that it is
	// done in steps, while actually it is done randomly, more over, the original field which is a shared
	// resources for the threads is overridden. there is a terrible unbeatable race-condition here
	// which causes each function to overwrite the results of some other random function, no sync. mechanism 
	// promises any order, it's a total chaos, and the results are un-predictable 
	private void forEach(StringFunction function) {
		List<String> newData = new ArrayList<String>();
		for (String str : data) {
			newData.add(function.transform(str));
		}
		data = newData;
	}

	// The threads were never started (missing threads.asStream.foreach(t->start())
	// The amount of threads might be huge - which is not effective
	// Need to relate to the number of CPUs / cores available 
	public List<String> transform(List<StringFunction> functions) throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>();
		for (final StringFunction f : functions) {
			threads.add(new Thread(new Runnable() {

				@Override
				public void run() {
					forEach(f);
				}
			}));
		}
		for (Thread t : threads) {
			t.join();
		}
		return data;
	}

	// Too bad to use string as input and output types as they immutable - would be better to use
	// StringBuilder
	public static interface StringFunction {
		public String transform(String str);
	}
}
