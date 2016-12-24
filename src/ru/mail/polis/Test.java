package ru.mail.polis;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Nechaev Mikhail
 * Since 24/12/16.
 */
public class Test {

    private final int MASK = 0b1111;
    private final Random r = new Random();

    public static void main(String[] args) {
        new Test().run(0b0100);
    }

    private final int TEST = 0b0001;

    Comparator<Integer> EVEN_FIRST = (v1, v2) -> {
        // Even first
        final int c = Integer.compare(v1 % 2, v2 % 2);
        return c != 0 ? c : Integer.compare(v1, v2);
    };

    private void smallTest(ISortedSet<Integer> set) {
        TreeSet<Integer> OK = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            assert set.contains(i) == OK.contains(i);
            assert set.add(i) == OK.add(i);
            assert set.contains(i) == OK.contains(i);
            assert set.add(i) == OK.add(i);
        }
        for (int i = 10; i >= 0; i--) {
            assert set.contains(i) == OK.contains(i);
            assert set.remove(i) == OK.remove(i);
            assert set.contains(i) == OK.contains(i);
            assert set.remove(i) == OK.remove(i);
        }
    }

    private void bigRandomTest(ISortedSet<Integer> set) {
        TreeSet<Integer> OK = new TreeSet<>();
        for (int i = 0; i < 1000; i++) {
            int next = r.nextInt(1000);
            assert set.contains(next) == OK.contains(next);
            assert set.add(next) == OK.add(next);
            assert set.contains(next) == OK.contains(next);
            assert set.add(next) == OK.add(next);
        }
        for (int i = 0; i < 1000; i++) {
            int next = r.nextInt(1000);
            assert set.contains(next) == OK.contains(next);
            assert set.remove(next) == OK.remove(i);
            assert set.contains(next) == OK.contains(next);
            assert set.remove(next) == OK.remove(i);
        }
    }

    private void EX() {
        System.exit(0);
    }

    private void run(int idx) {
        smallTest(new AVLTree<Integer>());
        smallTest(new RedBlackTree<Integer>());
        bigRandomTest(new AVLTree<Integer>());
        bigRandomTest(new RedBlackTree<Integer>());
        EX();
        if (test(0)) {
            testTree("avl", new AVLTree<Integer>(), null);
        }
        if (test(1)) {
            testTree("avlE", new AVLTree<Integer>(EVEN_FIRST), EVEN_FIRST);
        }
        if (test(2)) {
            testTree("RB", new RedBlackTree(), null);
        }
        if (test(3)) {
            testTree("RBE", new RedBlackTree(), EVEN_FIRST);
        }
    }

    private boolean test(int shift) {
        return (((MASK >> shift) & 1) == ((TEST >> shift) & 1));
    }

    private void testTree(String name, ISortedSet<Integer> set, Comparator<Integer> comp) {
        SortedSet<Integer> OK = new TreeSet<>(comp);
        System.err.println("-------------------- " + name + " -------------------");
        try {
            assert set.size() == 0;
            assert set.isEmpty();
            try {
                System.out.println(set.last());
                System.out.println(set.first());
                assert false;
            } catch (NoSuchElementException e) {
                System.out.println("OK");
            }
            for (int i = 0; i < 15; i++) {
                assert OK.contains(i) == set.contains(i);
                assert OK.add(i) == set.add(i);
                assert OK.contains(i) == set.contains(i);
                assert OK.add(i) == set.add(i);
            }
            System.out.println(set.inorderTraverse());
            assert set.size() == 15;
            assert OK.isEmpty() == set.isEmpty();
            assert OK.last().equals(set.last());
            assert OK.first().equals(set.first());
            for (int i = 15; i < 30; i++) {
                assert OK.contains(i) == set.contains(i);
                assert OK.add(i) == set.add(i);
                assert OK.contains(i) == set.contains(i);
                assert OK.add(i) == set.add(i);
                if (i % 2 == 0) {
                    assert OK.contains(i) == set.contains(i);
                    assert OK.remove(i) == set.remove(i);
                    assert OK.contains(i) == set.contains(i);
                    assert OK.remove(i) == set.remove(i);
                }
            }
            System.out.println(set.inorderTraverse());
            assert set.size() == 23;
            assert OK.isEmpty() == set.isEmpty();
            assert OK.contains(-1) == set.contains(-1);
            assert OK.contains(101) == set.contains(101);
            assert OK.contains(15) == set.contains(15);
            for (int i = 100; i >= 0; i--) {
                assert OK.contains(i) == set.contains(i);
                assert OK.remove(i) == set.remove(i);
                assert OK.contains(i) == set.contains(i);
                assert OK.remove(i) == set.remove(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("^^^^^^^^^^^^^^^^^^^^^ " + name + " ^^^^^^^^^^^^^^^^^^^");
    }
}
