package com.oracle.casb.common;

/**
 * Created By : abhijsri
 * Date  : 25/05/18
 **/
public  class  TreeNode<T extends Comparable<T>> {
    private T value;
    public TreeNode<T> left;
    public TreeNode<T> right;

    public TreeNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }
}
