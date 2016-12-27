package ru.mail.polis;

import java.util.Comparator;
import java.util.List;

//TODO: write code here
public class AVLTree<E extends Comparable<E>> implements ISortedSet<E> {

    class AVLNode<E extends Comparable<E>> extends Node<E> {

        private AVLNode<E> parent;

        public AVLNode(E value) {
            super(value);
        }

        public AVLNode<E> getParent() {
            return parent;
        }

        public void setParent(AVLNode<E> parent) {
            this.parent = parent;
        }
    }

    private AVLNode<E> root;
    private int size;
    private final Comparator<E> comparator;

    public AVLTree() {
        this.comparator = null;
    }

    public AVLTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public E first() {
        return null;
    }

    @Override
    public E last() {
        return null;
    }

    @Override
    public List<E> inorderTraverse() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(E value) {
        return false;
    }

    @Override
    public boolean add(E value) {
        return false;
    }

    @Override
    public boolean remove(E value) {
        return false;
    }

    @Override
    public Node<E> getRoot() {
        return root;
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }
}
