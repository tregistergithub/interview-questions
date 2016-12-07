package com.tal;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class BinarySearchTree {

	public static class Node {
		public final int i;
		public Node left, right;

		public Node(int i) {
			super();
			this.i = i;
		}

		@Override
		public String toString() {
			return String.valueOf(i);
		}
	}

	public static void main(String[] args) {
		Node bst = new Node(20);
		Stream.of(15, 200, 25, -5, 0, 100, 20, 12, 126, 1000, -150).forEach(v -> add(bst, v));
		System.out.println("Pre order");
		printTree_PreOrder(bst);
		System.out.println();
		System.out.println("Post order");
		printTree_PostOrder(bst);
		System.out.println();
		System.out.println("In order (asc)");
		printTree_InOrder(bst, true);
		System.out.println();
		System.out.println("In order (desc)");
		printTree_InOrder(bst, false);

		System.out.println();
		System.out.println("BFS");
		BFS(bst);
	}

	private static void add(Node head, Integer v) {
		Node curr = head;
		while (true) {
			if (v < curr.i) {
				if (curr.left == null) {
					curr.left = new Node(v);
					break;
				} else {
					curr = curr.left;
				}
			} else {
				if (curr.right == null) {
					curr.right = new Node(v);
					break;
				} else {
					curr = curr.right;
				}
			}
		}
	}

	private static void printTree_PostOrder(Node tree) {
		if (tree == null) {
			return;
		}
		printTree_PostOrder(tree.left);
		printTree_PostOrder(tree.right);
		System.out.print(tree + ", ");
	}

	private static void printTree_InOrder(Node tree, boolean asc) {
		if (tree == null) {
			return;
		}

		if (asc) {
			printTree_InOrder(tree.left, asc);
		} else {
			printTree_InOrder(tree.right, asc);
		}

		System.out.print(tree + ", ");

		if (asc) {
			printTree_InOrder(tree.right, asc);
		} else {
			printTree_InOrder(tree.left, asc);
		}
	}

	private static void printTree_PreOrder(Node tree) {
		if (tree == null) {
			return;
		}
		System.out.print(tree + ", ");
		printTree_PreOrder(tree.left);
		printTree_PreOrder(tree.right);

	}

	public static void BFS(Node root) {
		Queue<Node> q = new LinkedList<>();
		q.add(root);// You don't need to write the root here, it will be written
					// in the loop
		while (!q.isEmpty()) {
			Node n = q.remove();
			System.out.print(n + ", ");
			if (n.left != null) {
				q.add(n.left);// enqueue the left child
			}
			if (n.right != null) {
				q.add(n.right);// enqueue the right child
			}
		}
	}
}
