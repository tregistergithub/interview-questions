package com.tbla.tbz.q1;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

// TODO add interface
public class OldHashMapMemory implements OldMemory {
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
	public void put(String variableName, Integer value) {
		memory.put(variableName, value);		
	}
}
