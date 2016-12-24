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
public class TestTreeSet {

    private final int MASK = 0b1111;
    private final Random r = new Random();

    public static void main(String[] args) {
        new TestTreeSet().run(0b0100);
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
            check(OK, set, i, true);
        }
        for (int i = 10; i >= 0; i--) {
            check(OK, set, i, false);
        }
        for (int i = 0; i >= -10; i--) {
            check(OK, set, i, false);
        }
        for (int i = -10; i < 0; i++) {
            check(OK, set, i, true);
        }
    }

    private void bigRandomTest(ISortedSet<Integer> set) {
        TreeSet<Integer> OK = new TreeSet<>();
        for (int i = 0; i < 1000; i++) {
            check(OK, set, r.nextInt(1000), true);
        }
        for (int i = 0; i < 1000; i++) {
            check(OK, set, r.nextInt(1000), false);
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
            testTree("RB", new RedBlackTree<Integer>(), null);
        }
        if (test(3)) {
            testTree("RBE", new RedBlackTree<Integer>(), EVEN_FIRST);
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
                check(OK, set, i, true);
            }
            System.out.println(set.inorderTraverse());
            assert set.size() == 15;
            assert OK.isEmpty() == set.isEmpty();
            for (int i = 15; i < 30; i++) {
                check(OK, set, i, true);
                if (i % 2 == 0) {
                    check(OK, set, i, false);
                }
            }
            System.out.println(set.inorderTraverse());
            assert set.size() == 23;
            assert OK.isEmpty() == set.isEmpty();
            assert OK.contains(-1) == set.contains(-1);
            assert OK.contains(101) == set.contains(101);
            assert OK.contains(15) == set.contains(15);
            for (int i = 100; i >= 0; i--) {
                check(OK, set, i, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("^^^^^^^^^^^^^^^^^^^^^ " + name + " ^^^^^^^^^^^^^^^^^^^");
    }

    private void check(SortedSet<Integer> OK, ISortedSet<Integer> set, int value, boolean add) {
        assert OK.contains(value) == set.contains(value);
        assert OK.first().equals(set.first());
        assert OK.last().equals(set.last());
        assert OK.size() == set.size();
        assert OK.contains(value) == set.contains(value);
        if (add) assert OK.add(value) == set.add(value);
            else assert OK.remove(value) == set.remove(value);
        assert OK.size() == set.size();
        assert OK.contains(value) == set.contains(value);
        if (add) assert OK.add(value) == set.add(value);
           else assert OK.remove(value) == set.remove(value);
        assert OK.size() == set.size();
        assert OK.first().equals(set.first());
        assert OK.last().equals(set.last());
        assert OK.contains(value) == set.contains(value);
    }
}
