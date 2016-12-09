package com.tal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeHighestSalary {
	public static class Node implements Comparable<Node> {
		public Node(int salary, String name) {
			this.salary = salary;
			this.name = name;
		}

		int salary;
		String name;

		@Override
		public String toString() {
			return "name=" + name + ", salary=" + salary;
		}

		Node left, right;

		@Override
		public int compareTo(Node o) {
			return this.salary - o.salary;
		}
	}

	private static void printHighestSalaries(List<Node> s, int i) {
		for (int j = 0; j < i; j++) {
			System.out.println(s.get(j));			
		}
	}

	private static Node addSalary(Node tree, Node node) {
		if (tree == null) {
			return node;
		} else {
			Node curr = tree;
			while (true) {
				if (curr.salary <= node.salary) {
					if (curr.right == null) {
						curr.right = node;
						break;
					} else {
						curr = curr.right;
					}
				} else { // (curr.salary > node.salary)
					if (curr.left == null) {
						curr.left = node;
						break;
					} else {
						curr = curr.left;
					}
				}
			}
			return tree;
		}
	}

	public static void main(String[] args) {
		Node tree = null;
		List<Node> s = new ArrayList<>();
		addSalary(s, new Node(1000, "Shaul"));
		addSalary(s, new Node(2000, "Bingo"));
		addSalary(s, new Node(2500, "Miki"));
		addSalary(s, new Node(1500, "David"));
		addSalary(s, new Node(500, "Sima"));
		addSalary(s, new Node(500, "Jojo"));
		addSalary(s, new Node(500, "Loria"));
		addSalary(s, new Node(4500, "Piki"));
		
		
		
		addSalary(s, new Node(4000, "Burma"));
		addSalary(s, new Node(3000, "Sima"));
		addSalary(s, new Node(3000, "Tor"));
		addSalary(s, new Node(2500, "Eli"));

		Collections.sort(s, Collections.reverseOrder());

		printHighestSalaries(s, 3);

		//printHighestSalaries(tree, 3);
		System.out.println("Pre order");
		printTree_PreOrder(tree);
		System.out.println("Post order");
		printTree_PostOrder(tree);
		System.out.println("In order");
		printTree_InOrder(tree);
	}

	private static void addSalary(List<Node> s, Node node) {
		s.add(node);
	}

	private static void printTree_PostOrder(Node tree) {
		if (tree == null) {
			return;
		}
		printTree_PostOrder(tree.left);
		printTree_PostOrder(tree.right);
		System.out.println(tree);

	}

	private static void printTree_InOrder(Node tree) {
		if (tree == null) {
			return;
		}
		printTree_PostOrder(tree.left);
		System.out.println(tree);
		printTree_PostOrder(tree.right);

	}

	private static void printTree_PreOrder(Node tree) {
		if (tree == null) {
			return;
		}
		System.out.println(tree);
		printTree_PostOrder(tree.left);
		printTree_PostOrder(tree.right);

	}
}
