package com.tbla.tbz.calc;

public interface Memory {
	Integer get(String variableName);
	void put(String variableName, Integer value);
}
