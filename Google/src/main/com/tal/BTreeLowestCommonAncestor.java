package com.tal;

import java.util.List;
import java.util.Stack;

public class BTreeLowestCommonAncestor {
	public static class Node {
		public final int value;
		public Node left;
		public Node right;

		public void setKids(Node left, Node right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		public StringBuilder makeString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
			if (right != null) {
				right.makeString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
			}
			sb.append(prefix).append(isTail ? "└── " : "┌── ").append(value).append("\n");
			if (left != null) {

				left.makeString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
			}
			return sb;
		}

		public String toString2() {
			return this.makeString(new StringBuilder(), true, new StringBuilder()).toString();
		}

		public Node(int value) {
			super();
			this.value = value;
		}
	}

	private static void printLcaAndPaths(Node root, Node n1, Node n2) {
		Node lca = lowestCommonAncestor(root, n1, n2);

		System.out.println("The lowest common ancestor for " + n1 + " and " + n2 + " is: " + lca);
	}

	private static Node lowestCommonAncestor(Node root, Node n1, Node n2) {

		Node res = null;
		Stack<Node> pathToNode1 = new Stack<>();
		Stack<Node> pathToNode2 = new Stack<>();
		pathToNode1 = pathToNodeVer3(root, n1);
		pathToNode2 = pathToNodeVer3(root, n2);

		printPath(n1, pathToNode1);
		printPath(n2, pathToNode2);

		while (!pathToNode1.isEmpty() && !pathToNode2.isEmpty()) {
			Node step1 = pathToNode1.pop();
			Node step2 = pathToNode2.pop();
			if (step1 == step2) {
				res = step1;
			} else {
				break;
			}
		}
		return res;
	}

	private static void printPath(Node node, List<Node> pathToNode) {
		System.out.println("Path to " + node + " is: " + pathToNode);
	}

	private static Stack<Node> pathToNodeVer3(Node root, Node n) {
		if (root == null) {
			return null;
		} else if (root == n) {
			Stack<Node> path = new Stack<>();
			path.push(root);
			return path;
		} else {
			Stack<Node> leftPath = pathToNodeVer3(root.left, n);
			if (leftPath != null) {
				leftPath.push(root);
				return leftPath;
			} else {
				Stack<Node> rightPath = pathToNodeVer3(root.right, n);
				if (rightPath != null) {
					rightPath.push(root);
					return rightPath;
				} else {
					return null;
				}
			}
		}
	}

	public static void main(String[] args) {
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		Node n5 = new Node(5);
		Node n6 = new Node(6);
		Node n7 = new Node(7);
		Node n8 = new Node(8);

		n1.setKids(n3, n2);
		n3.setKids(n4, n6);
		n6.setKids(n5, null);
		n5.setKids(n7, n8);

		Node root = n1;

		System.out.println(root.makeString(new StringBuilder(), false, new StringBuilder()));

		printLcaAndPaths(root, n4, n5);
	}
}
