package ru.mail.polis;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChainHashTable<E extends Comparable<E>> extends AbstractSet<E> implements Set<E> {

    private final int INITIAL_CAPACITY = 8;
    private Comparator<E> comparator;
//    private final float LOAD_FACTOR = 0.5f;
    private Object[] table;
    private int size;

    public ChainHashTable() {
        this(null);
    }

    public ChainHashTable(Comparator<E> comparator) {
        this.comparator = comparator;
        this.table = new Object[INITIAL_CAPACITY];
    }

    @Override
    public boolean contains(Object value) {
        @SuppressWarnings("unchecked")
        E key = (E) value;

        Node curr = getNode(hash(key));
        while (curr != null && !curr.equals(value)) {
            curr = curr.next;
        }
        //Вышли по второму условию
        return curr != null;
    }

    @Override
    public boolean add(E value) {
        int idx = hash(value);
        if (table[idx] == null) {
            table[idx] = new Node(value);
        } else {
            Node curr = getNode(idx);
            while (curr.next != null && compare(value, curr.value) != 0) {
                curr = curr.next;
            }
            if (compare(value, curr.value) == 0) {
                return false;
            }
            curr.next = new Node(value);
        }
        size++;
        resize();
        return true;
    }

    @Override
    public boolean remove(Object value) {
        @SuppressWarnings("unchecked")
        E key = (E) value;

        Node prev = null;
        int idx = hash(key);
        Node curr = getNode(idx);
        while (curr != null && compare(key, curr.value) != 0) {
            prev = curr;
            curr = curr.next;
        }
        if (curr != null) {
            if (prev == null) { //head
                table[idx] = getNode(idx).next;
            } else {
                prev.next = curr.next;
            }
            curr.value = null;
            curr.next = null;
            size--;
            return true;
        }
        return false;
    }

    private int hash(E value) {
        return Math.abs(value.hashCode()) % table.length;
    }

    @SuppressWarnings("unchecked")
    private Node getNode(int idx) {
        return (Node) table[idx];
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        if (size * 2 < table.length) {
            return;
        }
        Object[] old = this.table;
        size = 0;
        table = new Object[table.length << 1];
        for (int i = 0; i < old.length; i++) {
            Node node = (Node) old[i];
            if (node != null) {
                Node curr = node;
                while (curr != null) {
                    Node next = curr.next;
                    //FIXME: insert value in head (all unique)
                    add(curr.value);
                    curr.next = null;
                    curr.value = null;
                    curr = next;
                }
                old[i] = null;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    private void print() {
        for (int i = 0; i < table.length; i++) {
            Node curr = getNode(i);
            System.out.println("idx = " + i + ", " + curr);
        }
        System.out.println("-------------------------");
    }

    class Node {
        E value;
        Node next;

        Node(E value) {
            this.value = value;
        }

        @Override
        public String toString() {
            List<E> values = new ArrayList<>();
            Node curr = this;
            while (curr != null) {
                values.add(curr.value);
                curr = curr.next;
            }
            return values.toString();
        }
    }

    public static void main(String[] args) {
        /*
        HashTableChain<Integer> table = new HashTableChain<>();
        for (int i = 0; i < 10; i++) {
            table.add(i);
        }
        table.print();
        for (int i = 5; i < 10; i++) {
            table.remove(i);
        }
        table.print();
        */
        ChainHashTable<String> ts = new ChainHashTable<>();
        ts.add("abc");
        ts.add("abc");
        ts.add("bcd");
        ts.add("cde");
        ts.add("qwerty");
        ts.add("polis");
        ts.print();
    }
}