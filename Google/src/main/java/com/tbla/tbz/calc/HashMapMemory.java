package com.tbla.tbz.calc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

// TODO add interface
public class HashMapMemory implements Memory {
	private Map<String, Integer> memory = new HashMap<>();

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
	public void put(String variableName, Integer value) {
		memory.put(variableName, value);		
	}
}
