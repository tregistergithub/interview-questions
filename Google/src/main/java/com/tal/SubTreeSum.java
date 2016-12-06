package com.tal;

public class SubTreeSum {
	public static class TreeNode {
		public TreeNode[] kids;
		public int value;

		public TreeNode(int value) {
			this.value = value;
		}

		public void setKids(TreeNode[] kids) {
			this.kids = kids;
		}

	}

	private static void printSum(String nodeName, TreeNode root) {
		int sum = calcSumOfTree(root);
		System.out.println("The sum of tree " + nodeName + " is: " + sum);

	}

	private static int calcSumOfTree(TreeNode node) {
		int selfValue = node.value;
		int kidsSum = 0;
		TreeNode[] allKids = node.kids;
		if (allKids != null) {
			for (TreeNode kid : allKids) {
				kidsSum += calcSumOfTree(kid);
			}
		}
		return selfValue + kidsSum;
	}

	public static void main(String[] args) {
		TreeNode t1 = new TreeNode(1);
		TreeNode t2 = new TreeNode(2);
		TreeNode t3 = new TreeNode(3);
		TreeNode t4 = new TreeNode(4);
		TreeNode t5 = new TreeNode(5);
		TreeNode t6 = new TreeNode(6);
		TreeNode t7 = new TreeNode(7);
		TreeNode t8 = new TreeNode(8);
		TreeNode t9 = new TreeNode(9);
		TreeNode t10 = new TreeNode(10);

		// 1
		// 2 3
		// 4 5 6 7
		// 8 9 10

		t1.setKids(new TreeNode[] { t2, t3 });
		t2.setKids(new TreeNode[] { t4, t5, t6 });
		t5.setKids(new TreeNode[] { t8, t9, t10 });
		t3.setKids(new TreeNode[] { t7 });

		printSum("t2", t2);
	}
}
