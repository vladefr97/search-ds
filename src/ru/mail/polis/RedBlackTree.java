package ru.mail.polis;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public class RedBlackTree<E extends Comparable<E>> extends AbstractSet<E> implements BalancedSortedSet<E> {

    private final Comparator<E> comparator;
    private Node root; //todo: Создайте новый класс если нужно. Добавьте новые поля, если нужно.
    private int size;
    Node nil = new Node();
    //todo: добавьте дополнительные переменные и/или методы если нужно

    public RedBlackTree() {
        this(null);
    }

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Вставляет элемент в дерево.
     * Инвариант: на вход всегда приходит NotNull объект, который имеет корректный тип
     *
     * @param value элемент который необходимо вставить
     * @return true, если элемент в дереве отсутствовал
     */
    @Override
    public boolean add(E value) {
        //todo: следует реализовать
        RBInsert(new Node(value));
        root = root;
        size++;
        return false;
    }

    private void RBInsert(Node z) {

        if (root == null) {
            root = z;
            root.color = Color.BLACK;
            root.right = nil;
            root.left = nil;
            return;
        }
        Node y = new Node();
        Node x = root;

        while (x != nil) {
            y = x;
            if (compare((E) x.value, (E) z.value) > 0)
                x = x.left;
            else x = x.right;
        }
        z.parent = y;
        if (y == nil) {
            root = z;
        } else {
            if (compare((E) z.value, (E) y.value) < 0)
                y.left = z;
            else y.right = z;
        }
        z.left = nil;
        z.right = nil;
        z.color = Color.RED;
        RBFixup(z);
    }

    private void RBFixup(Node z) {
        Node y = new Node();
        while (z.parent.color != Color.RED) {
            if (z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;

                } else if (z == z.parent.right) {
                    z=z.parent;
                    leftRotation();

                }
                z.parent.color=Color.BLACK;
                z.parent.parent.color=Color.RED;

            }

        }
    }

    private void leftRotation() {
    }


    /**
     * Удаляет элемент с таким же значением из дерева.
     * Инвариант: на вход всегда приходит NotNull объект, который имеет корректный тип
     *
     * @param object элемент который необходимо вставить
     * @return true, если элемент содержался в дереве
     */
    @Override
    public boolean remove(Object object) {
        @SuppressWarnings("unchecked")
        E value = (E) object;
        //todo: следует реализовать
        return false;
    }

    /**
     * Ищет элемент с таким же значением в дереве.
     * Инвариант: на вход всегда приходит NotNull объект, который имеет корректный тип
     *
     * @param object элемент который необходимо поискать
     * @return true, если такой элемент содержится в дереве
     */
    @Override
    public boolean contains(Object object) {
        @SuppressWarnings("unchecked")
        E value = (E) object;
        //todo: следует реализовать
        return false;
    }

    /**
     * Ищет наименьший элемент в дереве
     *
     * @return Возвращает наименьший элемент в дереве
     * @throws NoSuchElementException если дерево пустое
     */
    @Override
    public E first() {
        //todo: следует реализовать
        throw new NoSuchElementException("first");
    }

    /**
     * Ищет наибольший элемент в дереве
     *
     * @return Возвращает наибольший элемент в дереве
     * @throws NoSuchElementException если дерево пустое
     */
    @Override
    public E last() {
        //todo: следует реализовать
        throw new NoSuchElementException("last");
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "RBTree{" +
                "size=" + size + ", " +
                "tree=" + root +
                '}';
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        throw new UnsupportedOperationException("subSet");
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        throw new UnsupportedOperationException("headSet");
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        throw new UnsupportedOperationException("tailSet");
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("iterator");
    }

    /**
     * Обходит дерево и проверяет выполнение свойств сбалансированного красно-чёрного дерева
     * <p>
     * 1) Корень всегда чёрный.
     * 2) Если узел красный, то его потомки должны быть чёрными (обратное не всегда верно)
     * 3) Все пути от узла до листьев содержат одинаковое количество чёрных узлов (чёрная высота)
     *
     * @throws NotBalancedTreeException если какое-либо свойство невыполнено
     */
    @Override
    public void checkBalanced() throws NotBalancedTreeException {
        if (root != null) {
            if (root.color != Color.BLACK) {
                throw new NotBalancedTreeException("Root must be black");
            }
            traverseTreeAndCheckBalanced(root);
        }
    }

    private int traverseTreeAndCheckBalanced(Node node) throws NotBalancedTreeException {
        if (node == nil) {
            return 1;
        }
        int leftBlackHeight = traverseTreeAndCheckBalanced(node.left);
        int rightBlackHeight = traverseTreeAndCheckBalanced(node.right);
        if (leftBlackHeight != rightBlackHeight) {
            throw NotBalancedTreeException.create("Black height must be equal.", leftBlackHeight, rightBlackHeight, node.toString());
        }
        if (node.color == Color.RED) {
            checkRedNodeRule(node);
            return leftBlackHeight;
        }
        return leftBlackHeight + 1;
    }

    private void checkRedNodeRule(Node node) throws NotBalancedTreeException {
        if (node.left != null && node.left.color != Color.BLACK) {
            throw new NotBalancedTreeException("If a node is red, then left child must be black.\n" + node.toString());
        }
        if (node.right != null && node.right.color != Color.BLACK) {
            throw new NotBalancedTreeException("If a node is red, then right child must be black.\n" + node.toString());
        }
    }

    enum Color {
        RED, BLACK
    }

    static final class Node<E> {
        E value;
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        Color color = Color.BLACK;
        private boolean nil = true;

        public Node() {
        }

        public Node(E value) {
            this.value = value;
            nil = false;
            color = Color.RED;
        }


        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", left=" + left +
                    ", right=" + right +
                    //", parent=" + parent +
                    ", color=" + color +
                    '}';
        }
    }
}
