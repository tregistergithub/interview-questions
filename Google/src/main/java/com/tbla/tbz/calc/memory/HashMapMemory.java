package com.tbla.tbz.calc.memory;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Implementation of calculator Memory using a hash map
 *
 */
public class HashMapMemory implements Memory {
	private Map<String, Integer> memory = new TreeMap<>();

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Integer>> iter = memory.entrySet().iterator();
		if (iter.hasNext()) {
			sb.append('(');
		} else {
			sb.append("Memory is empty");
		}

		while (iter.hasNext()) {
			Entry<String, Integer> entry = iter.next();
			sb.append(entry.getKey());
			sb.append('=');
			sb.append(entry.getValue());
			if (iter.hasNext()) {
				sb.append(',');
			} else {
				sb.append(')');
			}
		}
		return sb.toString();
	}

	@Override
	public Integer get(String variableName) {
		return memory.get(variableName);
	}

	@Override
	public void set(String variableName, Integer value) {
		if (variableName == null) throw new RuntimeException( "Variable name must be non-null" );
		if (value == null) throw new RuntimeException( "Value name must be non-null" );
		memory.put(variableName, value);		
	}
}