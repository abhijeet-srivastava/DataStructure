package com.walmart;

import com.oracle.casb.common.TreeNode;

public class BuildTree {
    public static void main(String[] args) {
        BuildTree bt = new BuildTree();
        bt.testTreeCreate();
    }

    private void testTreeCreate() {
        int[] preorder = {3,9,20,15,7};
        int[] inorder = {9,3,15,20,7};
        TreeNode tree = buildTree(preorder, inorder);
        System.out.println(tree.getValue());
    }

    private TreeNode buildTree(int[] preorder, int[] inorder) {
        int[] pre= {0};
        return buildTree(pre, preorder, 0, inorder.length -1, inorder);
    }


    private TreeNode buildTree(int[] pInd, int[] preorder, int inStart, int inEnd, int[] inorder) {
        if(inStart > inEnd) {
            return null;
        }
        int index = findRootInOrder(preorder[pInd[0]], inStart, inEnd, inorder);
        TreeNode root = new TreeNode(inorder[index]);
        pInd[0] += 1;
        root.left = buildTree(pInd, preorder, inStart, index -1, inorder);
        root.right = buildTree(pInd, preorder,  index+1, inEnd, inorder);
        return root;
    }

    private int findRootInOrder(int root, int start, int end, int[] inorder){
        int index = start;
        while(inorder[index] != root && index <= end) {
            index += 1;
        }
        return index;
    }

}
