package com.tal;

public class FindLoopLength {

	private static final int NO_LOOP_FOUND = -1;

	public static class Node {
		public final int i;
		public boolean wasAlreadyVisited = false;
		public int distanceFromHead = 0;
		public Node next;

		public Node(int i) {
			this.i = i;
		}

		@Override
		public String toString() {
			return String.valueOf(i);
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
		Node n9 = new Node(9);

		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n7;
		n7.next = n8;
		n8.next = n9;
		n9.next = n3;

		Node head = n1;
		Node tail = n9;
		printLinkedList(head, tail);

		int calcLoopLength = calcLoopLength(head, 0);
		printIt(calcLoopLength);
		
		int calcLoopLengthWithPointer = calcLoopLengthWithPointers(head);
		printIt(calcLoopLengthWithPointer);
	}

	private static void printIt(int calcLoopLength) {
		if (calcLoopLength != NO_LOOP_FOUND) {
			System.out.println("The loop length is: " + calcLoopLength);
		} else {
			System.out.println("No loop found");
		}
	}

	private static int calcLoopLengthWithPointers(Node head) {
		Node slow = head;
		Node fast = head;
		
		slow = getNext(slow);
		fast = getNext(getNext(fast));
		
		boolean isLoopFound = false;
		while (!isLoopFound && slow != null && fast != null) {
			if ((slow != null && fast != null) && (slow == fast)) {
				isLoopFound = true;
				break;
			}
			slow = getNext(slow);
			fast = getNext(getNext(fast));
		}
		
		if (isLoopFound) {
			Node loopHead = slow;
			Node curr = getNext(slow);
			int loopLength = 1;
			while (curr != loopHead) {
				loopLength++;
				curr = getNext(curr);
			}
			
			return loopLength;
		} 
		
		return NO_LOOP_FOUND;
	}

	private static Node getNext(Node n) {
		if (n != null) {
			return n.next;
		} else {
			return null;
		}
	}

	private static int calcLoopLength(Node head, int distanceFromHead) {

		if (head.wasAlreadyVisited) {
			int loopLength = distanceFromHead - head.distanceFromHead;
			return loopLength;
			
		} else {
			head.wasAlreadyVisited = true;
			head.distanceFromHead = distanceFromHead;
			if (head.next != null) {
				return calcLoopLength(head.next, distanceFromHead + 1);
			} else {
				return NO_LOOP_FOUND;
			}
		}
	}

	private static void printLinkedList(Node head, Node tail) {
		if (head != tail) {
			System.out.print(head + " -> ");
			printLinkedList(head.next, tail);
		} else {
			System.out.println(head + " -> (" + head.next + ")");
		}
	}

}
