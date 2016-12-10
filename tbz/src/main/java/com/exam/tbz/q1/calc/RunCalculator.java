package com.exam.tbz.q1.calc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.exam.tbz.q1.memory.HashMapMemory;
import com.exam.tbz.q1.memory.Memory;

/**
 * Runs the calculator, accepts statments from stdin and outputs the memory to stdout at end
 *
 */
public class RunCalculator {

	private static Memory memory = new HashMapMemory();
	private static Calculator calc = new Calculator(memory);

	public static void main(String[] args) throws Exception {
		System.out.println("Please enter assinment statments (enter empty line to finish): ");

		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			String inputLine = null;
			while (true) {
				inputLine = br.readLine();
				if (!inputLine.isEmpty()) {
					calc.evaluate(inputLine);
				} else {
					break;
				}
			}

			System.out.println(memory);
		}
	}
}
