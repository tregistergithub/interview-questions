package com.tal;

import java.util.List;

public class BTreePrinter {

	final String name;
	final List<BTreePrinter> children;

	public BTreePrinter(String name, List<BTreePrinter> children) {
		this.name = name;
		this.children = children;
	}

	public void print() {
		print("", true);
	}

	public void print(String prefix, boolean isTail) {
		System.out.println(prefix + (isTail ? "L-- " : "L-- ") + name);
		for (int i = 0; i < children.size() - 1; i++) {
			children.get(i).print(prefix + (isTail ? "    " : "|   "), false);
		}
		if (children.size() > 0) {
			children.get(children.size() - 1).print(prefix + (isTail ? "    " : "|   "), true);
		}
	}
}
