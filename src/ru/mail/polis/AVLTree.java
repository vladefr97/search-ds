package ru.mail.polis;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

//TODO: write code here
public class AVLTree<E extends Comparable<E>> extends AbstractSet<E> implements SortedSet<E> {

    private final Comparator<E> comparator;
    //    private Node root;
    private int size;

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E first() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E last() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "AVLTree{" +
                "size=" + size +
                '}';
    }
}
