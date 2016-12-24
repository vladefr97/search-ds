package ru.mail.polis;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Created by Nechaev Mikhail
 * Since 24/12/16.
 */
public class Test {

    public static void main(String[] args) {
        new Test().run(0b0001);
    }

    private final int MASK = 0b1111;

    Comparator<Integer> EVEN_FIRST = (v1, v2) -> {
        // Even first
        final int c = Integer.compare(v1 % 2, v2 % 2);
        return c != 0 ? c : Integer.compare(v1, v2);
    };

    private void run(int idx) {
        if (test(idx,0)) testTree("avl", new AVLTree<>());
        if (test(idx,1)) testTree("avlE", new AVLTree<>(EVEN_FIRST));
        if (test(idx,2)) testTree("RB", new RedBlackTree<>());
        if (test(idx,3)) testTree("RBE", new RedBlackTree<>(EVEN_FIRST));
    }

    private boolean test(int idx, int shift) {
        return (((MASK >> shift) & 1) == ((idx >> shift) & 1));
    }

    private void testTree(String name, ISortedSet<Integer> set) {
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
                assert set.contains(i) != set.add(i);
                assert set.contains(i) != set.add(i);
            }
            System.out.println(set.inorderTraverse());
            assert set.size() == 15;
            assert !set.isEmpty();
            assert set.last() == 14;
            assert set.first() == 0;
            for (int i = 15; i < 30; i++) {
                set.add(i);
                assert set.contains(i) != set.add(i);
                assert set.contains(i) != set.add(i);
                if (i % 2 == 0) {
                    set.remove(i);
                    assert set.contains(i) == set.remove(i);
                    assert set.contains(i) == set.remove(i);
                }
            }
            System.out.println(set.inorderTraverse());
            assert set.size() == 22;
            assert !set.isEmpty();
            assert !set.contains(-1);
            assert !set.contains(101);
            assert set.contains(15);
            for (int i = 100; i >= 0; i++) {
                assert set.contains(i) == set.remove(i);
                assert set.contains(i) == set.remove(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("^^^^^^^^^^^^^^^^^^^^^ " + name + " ^^^^^^^^^^^^^^^^^^^");
    }
}
