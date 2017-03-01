package org.nyjsl.algorithmanddatastructure;

/**
 * Created by pc on 2017/2/24.
 */

public class BinarySearchTree<T extends Comparable<? super T>> {


    private BinaryNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void makeEmpty(){
        this.root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public boolean contains(T item){
        return contains(item,root);
    }


    private boolean contains(T item,BinaryNode<T> t){
        if (null == t) {
            return false;
        }
        final int result = item.compareTo(t.element);
        if(result>0){
            return contains(item, t.rightChild);
        } else if (result < 0) {
            return contains(item, t.lefChild);
        } else {
            return true;
        }
    }


    public T findMax(){
        if (root == null) {
            return null;
        }
        return findMax(root).element;
    }

    private BinaryNode<T> findMax(BinaryNode<T> node){
        if (node == null) {
            return null;
        }else if(node.rightChild == null){
            return node;
        }else{
            return findMax(node.rightChild);
        }
    }

    public T findMin(){
        if (root == null) {
            return null;
        }
        return findMin(root).element;
    }

    private BinaryNode<T> findMin(BinaryNode<T> root) {
        if (root == null) {
            return null;
        }
        while(root.lefChild != null){
            root = root.lefChild;
        }
        return root;
    }

    public void insert(T item){
        this.root = insert(item,root);
    }

    private BinaryNode<T> insert(T item,BinaryNode<T> node){
        if (null == node) {
            return new BinaryNode<T>(item, null, null);
        }
        final int result = item.compareTo(node.element);
        if (result > 0) {
            node.rightChild =  insert(item, node.rightChild);
        } else if (result < 0) {
            node.lefChild = insert(item, node.lefChild);
        }
        return node;
    }

    public void remove(T item) {
        this.root = remove(item, root);
    }

    public void printTree(){
        printTree(this.root);
    }

    private void printTree(BinaryNode<T> node){
        if(node == null){
            return;
        }
        printTree(node.lefChild);
        System.out.println("element = [" + node.element + "]");
        printTree(node.rightChild);
    }


    private BinaryNode<T> remove(T item,BinaryNode<T> node){
        if( node == null){
            return node;
        }
        final int result = item.compareTo(node.element);
        if (result > 0) {
            node.rightChild  = remove(item, node.rightChild);
        }else if(result<0){
            node.lefChild = remove(item, node.lefChild);
        } else if (node.lefChild!= null && node.rightChild!= null) {
            node.element = findMin(node.rightChild).element;
            node.rightChild = remove(node.element, node.rightChild);
        }else{
            node = node.lefChild != null? node.lefChild:node.rightChild;
        }
        return node;
    }

    private static class BinaryNode<T>{

        T element;
        BinaryNode<T> lefChild;
        BinaryNode<T> rightChild;

        public BinaryNode(T element) {
            this(element,null,null);
        }

        public BinaryNode(T element, BinaryNode<T> lefChild, BinaryNode<T> rightChild) {
            this.element = element;
            this.lefChild = lefChild;
            this.rightChild = rightChild;
        }
    }
}
