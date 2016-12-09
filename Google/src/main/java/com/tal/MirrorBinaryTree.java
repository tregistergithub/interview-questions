package com.tal;

import java.util.stream.Stream;

public class MirrorBinaryTree {
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

	public static void printBinaryTree(Node root, int level){
	    if(root==null)
	         return;
	    printBinaryTree(root.right, level+1);
	    if(level!=0){
	        for(int i=0;i<level-1;i++)
	            System.out.print("|\t");
	            System.out.println("|-------"+root.i);
	    }
	    else
	        System.out.println(root.i);
	    printBinaryTree(root.left, level+1);
	}    
	
	public static void main(String[] args) {
		Node bst = new Node(20);
		Stream.of(15, 200, 25, -5, 0, 100, 20, 12, 126, 1000, -150).forEach(v -> add(bst, v));
		printBinaryTree(bst, 0);
//		mirrorTreeInPlace(bst);
//		System.out.println("mirror in place:");
//		printBinaryTree(bst, 0);
		
		Node bst2 = new Node(20);
		Stream.of(15, 200, 25, -5, 0, 100, 20, 12, 126, 1000, -150).forEach(v -> add(bst2, v));
//		System.out.println("new mirror :");
//		Node mirror = new Node(bst2.i);
//		mirrorTree(bst2, mirror);
//		printBinaryTree(mirror, 0);

		System.out.println("new dup :");
		Node dup = new Node(bst2.i);
		dupTree(bst2, dup);
		printBinaryTree(dup, 0);
	}
	
	private static void mirrorTree(Node tree, Node mirror) {
		if (tree.left != null) {
			Node newRight = new Node(tree.left.i);
			mirrorTree(tree.left, newRight);
			mirror.right = newRight;
		}
		if (tree.right != null) {
			Node newLeft = new Node(tree.right.i);
			mirrorTree(tree.right, newLeft);
			mirror.left = newLeft;
		}
	}
	
	private static void dupTree(Node tree, Node dup) {
		if (tree.left != null) {
			Node newLeft = new Node(tree.left.i);
			dupTree(tree.left, newLeft);
			dup.left = newLeft;
		}
		if (tree.right != null) {
			Node newRight = new Node(tree.right.i);
			dupTree(tree.right, newRight);
			dup.right = newRight;
		}
	}

	private static void mirrorTreeInPlace(Node n) {
		if (n.left != null) {
			mirrorTreeInPlace(n.left);
		}
		if (n.right != null) {
			mirrorTreeInPlace(n.right);
		}
		
		Node temp = n.left;
		n.left = n.right;
		n.right = temp;
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
}
