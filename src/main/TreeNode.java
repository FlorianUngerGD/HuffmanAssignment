package main;

public class TreeNode {
	private TreeNode left;
	private TreeNode right;
	private Integer frequencyValue;
	private Integer charValue;

	public TreeNode (Integer frequencyValue, Integer charValue) {
		this.frequencyValue = frequencyValue;
		this.charValue = charValue;
	}

	public TreeNode (Integer frequencyValue, TreeNode left, TreeNode right) {
		this.frequencyValue = frequencyValue;
		this.left = left;
		this.right = right;
	}

	public boolean isLeaf() {
		return left == null && right == null;
	}

	public TreeNode getLeft() {
		return left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public Integer getFrequencyValue() {
		return frequencyValue;
	}

	public void setFrequencyValue(Integer frequencyValue) {
		this.frequencyValue = frequencyValue;
	}

	public Integer getCharValue() {
		return charValue;
	}

	public void setCharValue(Integer charValue) {
		this.charValue = charValue;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}
}
