package ru.mail.polis;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

//TODO: write code here
public class RedBlackTree<E extends Comparable<E>> extends AbstractSet<E> implements SortedSet<E> {

    private int size;
    private final Comparator<E> comparator;

    public RedBlackTree() {
        this(null);
    }

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    @Override
    public Comparator<? super E> comparator() {
        return null;
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
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
