package ru.mail.polis;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import javax.swing.plaf.synth.SynthOptionPaneUI;

/**
 * Created by Nechaev Mikhail
 * Since 24/12/16.
 */
public class TestTreeSet {

    private final Random r = new Random();

    private final Comparator<Integer> EVEN_FIRST = (v1, v2) -> {
        // Even first
        final int c = Integer.compare(v1 % 2, v2 % 2);
        return c != 0 ? c : Integer.compare(v1, v2);
    };

    Comparator<Integer> ALL_EQUALS = (v1, v2) -> { return 0; };

    public static void main(String[] args) {
        new TestTreeSet().run();
    }

    private void run() {
        run2(() -> {
            argh();
            return null;
        });
        AVL();
        RB();
    }

    private void AVL() {
        run2(() -> { checkEmptyAndNull(new AVLTree<>()); return null; });
        run2(() -> { smallTest(new AVLTree<Integer>()); return null; });
        run2(() -> {
            bigRandomTest(new AVLTree<Integer>());
            return null;
        });
        run2(() -> {
            testTree("AVL", new AVLTree<Integer>(), null);
            return null;
        });
        run2(() -> {
            testTree("AVLE", new AVLTree<Integer>(EVEN_FIRST), EVEN_FIRST);
            return null;
        });
    }

    private void RB() {
        run2(() -> { checkEmptyAndNull(new RedBlackTree<>()); return null; });
        run2(() -> { smallTest(new RedBlackTree<Integer>()); return null; });
        run2(() -> {
            bigRandomTest(new RedBlackTree<Integer>());
            return null;
        });
        run2(() -> {
            testTree("RB", new RedBlackTree<Integer>(), null);
            return null;
        });
        run2(() -> {
            testTree("RBE", new RedBlackTree<Integer>(EVEN_FIRST), EVEN_FIRST);
            return null;
        });
    }

    private void argh() {
        int LEN = 10;
        SortedSet<Integer> OK = new TreeSet<>();
        ISortedSet<Integer> set = new AVLTree<>();
//      ISortedSet<Integer> set = new RedBlackTree<>();
        for (int value = 0; value < LEN; value++) {
            check(OK, set, value, true);
        }
        for (int value = LEN; value >= 0; value--) {
            check(OK, set, value, false);
        }
    }

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
        SortedSet<Integer> OK = new TreeSet<>();
        for (int i = 0; i < 1000 ; i++) {
            check(OK, set, r.nextInt(1000), true);
        }
        for (int i = 0; i < 1000; i++) {
            check(OK, set, r.nextInt(1000), false);
        }
    }

    private void testTree(String name, ISortedSet<Integer> set, Comparator<Integer> comp) {
        SortedSet<Integer> OK = new TreeSet<>(comp);
        System.out.println("-------------------- " + name + " -------------------");
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
        System.out.println("^^^^^^^^^^^^^^^^^^^^^ " + name + " ^^^^^^^^^^^^^^^^^^^");
    }

    private void check(SortedSet<Integer> OK, ISortedSet<Integer> set, int value, boolean add) {
        assert OK.contains(value) == set.contains(value);
        if (!OK.isEmpty()) {
            assert OK.first().equals(set.first());
            assert OK.last().equals(set.last());
        }
        assert OK.size() == set.size();
        assert OK.contains(value) == set.contains(value);
        if (add) assert OK.add(value) == set.add(value);
            else assert OK.remove(value) == set.remove(value);
        assert OK.size() == set.size();
        assert OK.contains(value) == set.contains(value);
        if (add) assert OK.add(value) == set.add(value);
           else assert OK.remove(value) == set.remove(value);
        assert OK.size() == set.size();
        assert OK.contains(value) == set.contains(value);
        if (!OK.isEmpty()) {
            assert OK.first().equals(set.first());
            assert OK.last().equals(set.last());
        }
    }

    private void checkEmptyAndNull(ISortedSet<Integer> set) {
        assert set.isEmpty();
        assert set.size() == 0;
        try {
            set.first(); assert false;
        } catch (NoSuchElementException e) {}
        try {
            set.last(); assert false;
        } catch (NoSuchElementException e) {}
        try {
            set.add(null); assert false;
        } catch (NullPointerException e) {}
        try {
            set.remove(null); assert false;
        } catch (NullPointerException e) {}
    }

    public void run2(Callable<Void> callable) {
        try {
            callable.call();
        } catch (AssertionError | Exception ae) {
            ae.printStackTrace();
        }
    }
}
