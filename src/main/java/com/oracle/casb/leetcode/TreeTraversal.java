package com.oracle.casb.leetcode;

import com.oracle.casb.common.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Created By : abhijsri
 * Date  : 20/11/18
 **/
public class TreeTraversal {

    public static void main(String[] args) {

        TreeTraversal tt = new TreeTraversal();
        //tt.testZigZag();
        tt.isValidSerialization("9,3,4,#,#,1,#,#,2,#,6,#,#");
    }

    private void testZigZag() {
        TreeNode<Integer> root = createTree();
        List<Integer> res = zigzagTraversal(root);
        System.out.printf("%s%c", res.stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]")),'\n');
    }

    private TreeNode<Integer> createTree() {
        TreeNode<Integer> root = new TreeNode<>(25);
        root.setLeft(new TreeNode<>(12));
        root.setRight(new TreeNode<>(37));

        root.getLeft().setLeft(new TreeNode<>(6));
        root.getLeft().getLeft().setRight(new TreeNode<>(15));
        root.getLeft().setRight(new TreeNode<>(18));

        root.getLeft().getRight().setLeft(new TreeNode<>(21));

        root.getRight().setLeft(new TreeNode<>(27));
        root.getRight().setRight(new TreeNode<>(45));

        return root;
    }

    private List<Integer> iterativeInorder(TreeNode<Integer> root) {
        List<Integer> inorder = new ArrayList<>();
        if (root == null) {
            return inorder;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            root = stack.pop();
            inorder.add(root.getValue());
            root = root.getRight();
        }
        return inorder;
    }

    private int kthSmallest(TreeNode<Integer> root, int k) {
        if (root == null) {
            return -1;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (root != null || stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            root = stack.pop();
            if (--k == 0) {
                return root.getValue();
            }
            root = root.getRight();
        }
        return -1;
    }
    private boolean isValidBst(TreeNode<Integer> root) {
        boolean isSorted = true;
        if (root == null) {
            return isSorted;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        TreeNode<Integer> prev = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            root = stack.peek();
            if (prev != null && prev.getValue() > root.getValue()) {
                isSorted = false;
                break;
            }
            prev = root;
            root = root.getRight();
        }
        return isSorted;
    }

    private List<Integer> iterativePostOrder1Stack(TreeNode<Integer> root) {
        List<Integer> postorder = new ArrayList<>();
        if (root == null) {
            return postorder;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                if (root.getLeft() != null) {
                    root = root.getLeft();
                } else {
                    root = root.getRight();
                }
            }
            TreeNode<Integer> node =  stack.pop();
            postorder.add(node.getValue());
            if (!stack.isEmpty() && stack.peek().getLeft() == node) {
                root = stack.peek().getRight();
            }
        }
        return postorder;
    }
    private List<TreeNode<Integer>> iterativePostOrder2Stack(TreeNode<Integer> root) {
        //Left -> Right -> Node
        List<TreeNode<Integer>> postorder = new ArrayList<>();
        if (root == null) {
            return postorder;
        }
        Stack<TreeNode<Integer>> stack1 = new Stack<>();
        Stack<TreeNode<Integer>> stack2 = new Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            root = stack1.pop();
            stack2.push(root);
            if (root.getLeft() != null) {
                stack1.push(root.getLeft());
            }
            if (root.getRight() != null) {
                stack1.push(root.getRight());
            }
        }
        while (!stack2.isEmpty()) {
            postorder.add(stack2.pop());
        }
        return postorder;
    }
    private List<TreeNode<Integer>> iterativePreOrder(TreeNode<Integer> root) {
        //root -> Left -> Right
        List<TreeNode<Integer>> preorder = new ArrayList<>();
        if (root == null) {
            return preorder;
        }
        Stack<TreeNode<Integer>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            preorder.add(root);
            if (root.getRight() != null) {
                stack.push(root.getRight());
            }
            if (root.getLeft() != null) {
                stack.push(root.getLeft());
            }
        }
        return preorder;
    }
    TreeNode<Integer> lowestCommonAncestor(TreeNode<Integer> root, TreeNode<Integer> node1, TreeNode<Integer> node2) {
        if (root == null) {
            return null;
        }
        if (root == node1
        || root == node2 ) {
            return root;
        }
        TreeNode<Integer> left = lowestCommonAncestor(root.getLeft(), node1, node2);
        TreeNode<Integer> right = lowestCommonAncestor(root.getRight(), node1, node2);
        if (left != null && right != null) {
            return root;
        }
        return left == null ? right : left;
    }

    private void traverseInOrder(TreeNode<Integer> root) {
        Stack<TreeNode<Integer>> stack = new Stack<>();
        TreeNode<Integer> current = root;
        while (current != null || !stack.empty()) {
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }
            current = stack.pop();
            System.out.println("Node " + current.getValue());
            current = current.getRight();

        }

    }

    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.length() == 0) {
            return true;
        }

        String[] nodes = preorder.split(",");
        Stack<String> stack = new Stack<>();

        for (String node : nodes) {
            System.out.printf("current token : %s\n", node);
            if (node.equals("#")) {
                while (!stack.isEmpty() && stack.peek().equals("#")) {
                    System.out.printf("Stack Peek : %s\n", stack.peek());
                    stack.pop();
                    if (stack.isEmpty()) {
                        return false;
                    }
                    System.out.printf("After empty check Stack Peek : %s\n", stack.peek());
                    stack.pop();
                }
            }

            stack.push(node);
        }

        return stack.size() == 1 && stack.peek().equals("#");
    }

    private void findSuccPred(TreeNode<Integer> root, int key, TreeNode<Integer>[] succPre) {
        TreeNode<Integer> succ = null;
        TreeNode<Integer> pre = null;
        if (root == null) {
            return;
        } else if (root.getValue() < key) {
            succPre[1] = root;
            findSuccPred(root.getRight(), key, succPre);
        } else if (root.getValue() > key) {
            succPre[0] = root;
            findSuccPred(root.getLeft(), key, succPre);
        } else { //if (root.getValue() == key) {
            TreeNode<Integer> temp = null;
            if (root.getRight() != null) {
                temp = root.getRight();
                while (temp.getLeft() != null) {
                    temp = temp.getLeft();
                }
                succPre[0] = temp;
            }
            if (root.getLeft() != null) {
                temp = root.getLeft();
                while (temp.getRight() != null) {
                    temp = temp.getRight();
                }
                succPre[0] = temp;
            }
        }
    }
    public  int dynamic(int[] v, int amount) {
        int[][] dynamic = new int[v.length+1][amount+1];
        dynamic[0][0] = 0;
        //if no coins given, 0 ways to change the amount
        //if amount=0 then just return empty set to make the change
        for (int coinIndex = 0; coinIndex <= v.length; coinIndex++) {
            dynamic[coinIndex][0] = 1;
        }
        for (int coinIndex = 1; coinIndex <= v.length; coinIndex++) {
            for (int amnt = 1; amnt <= amount; amnt++) {
                // check if the coin value is less than the amount needed
               if(v[coinIndex-1] <= amnt) {
                   // reduce the amount by coin value and
                   // use the subproblem solution (amount-v[i]) and
                   // add the solution from the top to it
                   dynamic[coinIndex][amnt]
                           = dynamic[coinIndex - 1][amnt]
                           + dynamic[coinIndex][amnt - v[coinIndex-1]];

               } else {
                    dynamic[coinIndex][amnt] = dynamic[coinIndex-1][amnt];
               }
            }
        }
        return dynamic[v.length][amount];
    }

    private List<Integer> zigzagTraversal(TreeNode<Integer> root) {
        List<Integer> list = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        LinkedList<TreeNode> child = new LinkedList<>();
        queue.add(root);
        boolean isEven = true;
        while (!queue.isEmpty()) {
            TreeNode<Integer> node = queue.poll();
            list.add(node.getValue());
            if (isEven) {
                if(node.getLeft() != null) {
                    child.add(0, node.getLeft());
                }
                if (node.getRight() != null) {
                    child.add(0, node.getRight());
                }
            } else {
                if (node.getRight() != null) {
                    child.add(0, node.getRight());
                }
                if(node.getLeft() != null) {
                    child.add(0, node.getLeft());
                }
            }
            if (queue.isEmpty()) {
                //Collections.reverse(child);
                queue.addAll(child);
                child.clear();
                isEven = !isEven;
            }
        }
        return list;
    }
    
    private List<Integer> printBoundryBinaryTree(TreeNode<Integer> root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        res.add(root.getValue());
        Map<Integer, Integer> map = new HashMap<>();
        int level = 2;
        Queue<TreeNode<Integer>> main = new LinkedList<>();
        Queue<TreeNode<Integer>> secondary = new LinkedList<>();
        List<Integer> bottomLevel = new ArrayList<>();
        main.add(root);
        TreeNode<Integer> first = null;
        while (!main.isEmpty()) {
            TreeNode<Integer> current = main.poll();
            if (isLeaf(current)) {
                bottomLevel.add(current.getValue());
                continue;
            }
            if (first == null && current != root) {
                res.add(current.getValue());
                first = current;
            }
            secondary.add(current.getLeft());
            secondary.add(current.getRight());
            if (main.isEmpty()) {
                first = null;
                if (current != root) {
                    map.put(level, current.getValue());
                }
                level += 1;
                main = secondary;
                secondary.clear();
            }
        }
        res.addAll(bottomLevel);
        for (int i = level - 1; i > 1; i--) {
            res.add(map.get(i));
        }
        return res;
    }

    private boolean isLeaf(TreeNode<Integer> node) {
        return !(node.getLeft() == null || node.getRight() == null);
    }
}
