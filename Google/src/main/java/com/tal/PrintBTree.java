package com.tal;

import java.util.ArrayList;
import java.util.List;

public class PrintBTree {
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

		public Node(int value) {
			super();
			this.value = value;
		}
	}

	private static final Node EMPTY_NODE = new Node(1);

	private static void print(List<Node> nodeList) {

		List<Node> newNodeList = new ArrayList<>();
		int addedNodes = 0;
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			if (node == EMPTY_NODE) {
				System.out.print(".");
			} else {
				System.out.print(node.value);
				if (node.left != null) {
					newNodeList.add(node.left);
					addedNodes++;
				} else {
					newNodeList.add(EMPTY_NODE);

				}
				if (node.right != null) {
					newNodeList.add(node.right);
					addedNodes++;
				} else {
					newNodeList.add(EMPTY_NODE);
				}
			}
		}

		if (addedNodes > 0) {
			System.out.println();
			print(newNodeList);
		}

	}

	public static void main(String[] args) {
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		Node n5 = new Node(5);
		Node n6 = new Node(6);
		// Node n7 = new Node(7);
		
		

		n1.setKids(n3, n2);
		n3.setKids(n4, n6);
		n6.setKids(n5, null);

		Node root = n1;

		List<Node> nodeList = new ArrayList<>();
		nodeList.add(root);
		print(nodeList);
	}
}
