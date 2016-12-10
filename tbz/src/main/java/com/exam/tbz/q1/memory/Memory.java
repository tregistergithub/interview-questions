package com.exam.tbz.q1.memory;

/**
 * Represents the memory that the calculator use
 * 
 * The actions it supports: set & get of Integer values
 *
 */
public interface Memory {
	Integer get(String variableName);
	void set(String variableName, Integer value);
}
