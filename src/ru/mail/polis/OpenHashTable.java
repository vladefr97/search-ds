package ru.mail.polis;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

//TODO: write code here
public class OpenHashTable<E extends Comparable<E>> extends AbstractSet<E> implements Set<E> {

    private Comparator<E> comparator;

    public OpenHashTable() {
        this(null);
    }

    public OpenHashTable(Comparator<E> comparator) {
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

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

}
