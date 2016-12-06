package com.tal;

public class LinkedListFindNthElementFromEnd {
	public static class Node {
		public final int value;
		public Node next;

		public Node(int value) {
			this.value = value;
		}
	}

	public static void printList(Node head) {
		Node curr = head;
		while (curr != null) {
			System.out.print(curr.value);
			curr = curr.next;
			if (curr != null) {
				System.out.print(", ");
			}
		}
		System.out.println();
	}

	public static int getListLength(Node head, int n) {
		int len = 1;
		if (head.next != null) {
			len = getListLength(head.next, n) + 1;
			if (len == n) {
				System.out.println(head.value);
			}
		}
		return len;
	}

	public static void main(String args[]) {
		Node n1 = new Node(1);
		Node n2 = new Node(2); n1.next = n2;
		Node n3 = new Node(3); n2.next = n3;
		Node n4 = new Node(4); n3.next = n4;
		Node n5 = new Node(5); n4.next = n5;
		Node n6 = new Node(6); n5.next = n6;
		Node n7 = new Node(7); n6.next = n7;
		Node n8 = new Node(8); n7.next = n8;
		Node n9 = new Node(9); n8.next = n9;
		Node n10 = new Node(10); n9.next = n10;

		Node list = n1;

		printList(list);

		int n = 2;
		getListLength(list, n);
	}
}
