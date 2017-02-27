package org.nyjsl;

/**
 * Created by pc on 2017/2/24.
 */

public class AVLTree<T extends Comparable<? super T>> {

    AVLNode<T> root;

    private static class AVLNode<T> {

        public AVLNode(T element) {
            this(element, null, null);
        }

        public AVLNode(T element, AVLNode<T> left, AVLNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        T element;
        AVLNode<T> left;
        AVLNode<T> right;
        int height;

    }

    public void printTree(){
        printTree(root);
    }

    private void printTree(AVLNode<T> node){
        if(node == null){
            return;
        }
        printTree(node.left);
        System.out.println("element = [" + node.element + "]");
        printTree(node.right);
    }


    private int height(AVLNode<T> node){
        return null == node ? -1: node.height;
    }

    public void insert(T element){
        this.root = insert(element,root);
    }

    private AVLNode<T> insert(T element, AVLNode<T> node) {
        if (node == null) {
            return new AVLNode<T>(element, null, null);
        }
        final int result = element.compareTo(node.element);
        if (result > 0) {
            node.right = insert(element,node.right);

            if(height(node.left) - height(node.right) == 2){

                if(element.compareTo(node.right.element)>0){
                    node = rotateWithRightChild(node);
                }else{
                    node = doubleRotateWithRightChild(node);
                }
            }

        } else if (result < 0) {
            node.left = insert(element, node.left);
            if(height(node.left) - height(node.right) == 2){

                if(element.compareTo(node.left.element)<0){
                    node = rotateWithLeftChild(node);
                }else{
                    node = doubleRotateWithLeftChild(node);
                }
            }
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node ;
    }

    private AVLNode<T> doubleRotateWithRightChild(AVLNode<T> node) {
        node.right = rotateWithLeftChild(node.right);
        return  rotateWithRightChild(node);
    }

    private AVLNode<T> rotateWithRightChild(AVLNode<T> node) {
        final AVLNode<T> node1 = node.right;
        node.right = node1.left;
        node1.left = node;
        node.height = Math.max(height(node.left), height(node.right))+1;
        node1.height = Math.max(height(node1.left), height(node1.right))+1;
        return node1;
    }

    private AVLNode<T> doubleRotateWithLeftChild(AVLNode<T> node) {
        node.left = rotateWithLeftChild(node.left);
        return  rotateWithLeftChild(node);
    }

    private AVLNode<T> rotateWithLeftChild(AVLNode<T> node) {
        final AVLNode<T> node1 = node.left;
        node.left = node1.right;
        node1.right = node;
        node.height = Math.max(height(node.left), height(node.right))+1;
        node1.height = Math.max(height(node1.left), height(node1.right))+1;
        return node1;
    }


}
