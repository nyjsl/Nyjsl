package org.nyjsl;

/**
 * Created by pc on 2017/2/24.
 */

public class Test {

    public static void main(String[] args) {

        testBST();
        System.out.println("=====================================================");
        testAVL();
    }

    private static void testAVL() {

        AVLTree avl = new AVLTree();
        avl.insert(new Integer(5));
        avl.insert(new Integer(2));
        avl.insert(new Integer(6));
        avl.insert(new Integer(9));
        avl.insert(new Integer(1));
        avl.printTree();
    }

    private static void testBST(){
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(new Integer(5));
        bst.insert(new Integer(2));
        bst.insert(new Integer(6));
        bst.insert(new Integer(9));
        bst.insert(new Integer(1));
        bst.printTree();
        bst.remove(new Integer(5));
        bst.remove(new Integer(6));
        bst.printTree();
    }
}
