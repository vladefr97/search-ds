package ru.mail.polis;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public class AVLTree<E extends Comparable<E>> extends AbstractSet<E> implements BalancedSortedSet<E> {


    class Node {

        E value;
        private Node left, right, father;

        public Node(Node father, E value) {
            this.value = value;
            this.left = this.right = null;
            this.father = father;
        }

        public Node(E value) {
            this.value = value;
        }

        public Node() {
            this(null, null);
        }
    }

    private final Comparator<E> comparator;

    private Node root; //todo: Создайте новый класс если нужно. Добавьте новые поля, если нужно.
    private int size;
    //todo: добавьте дополнительные переменные и/или методы если нужно

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Вставляет элемент в дерево.
     * Инвариант: на вход всегда приходит NotNull объект, который имеет корректный тип
     *
     * @param value элемент который необходимо вставить
     * @return true, если элемент в дереве отсутствовал
     */
    private boolean elemInTree = false;

    @Override
    public boolean add(E value) {
        //todo: следует реализовать
        root = add(root, value, null);


        if (elemInTree) {
            elemInTree = false;
            return false;
        } else {
            size++;

            return true;
        }
    }

    private Node add(Node curr, E value, Node father) {

        if (curr == null) {
            return new Node(father, value);
        }

        int cmp = compare(curr.value, value);//curr.value.compareTo(value);
        if (cmp > 0)
            curr.left = add(curr.left, value, curr);
        else if (cmp < 0)
            curr.right = add(curr.right, value, curr);
        else {
            elemInTree = true;
            return curr;
        }

        int height = getHeight(curr.left) - getHeight(curr.right);
        if (height == 2)
            curr = rightRotation(curr);
        else if (height == -2)
            curr = leftRotation(curr);
        return curr;
    }


    /**
     * Удаляет элемент с таким же значением из дерева.
     * Инвариант: на вход всегда приходит NotNull объект, который имеет корректный тип
     *
     * @param object элемент который необходимо вставить
     * @return true, если элемент содержался в дереве
     */

    private boolean elemIsDeleted = true;//Переменная, которая устанавливается в false, если удаляемый елемент не был найден в дереве

    @Override
    public boolean remove(Object object) {

        @SuppressWarnings("unchecked")
        E value = (E) object;
        //todo: следует реализовать
        remove(root, value);
        if (elemIsDeleted) {
            size--;
            return elemIsDeleted;
        } else {
            elemIsDeleted = true;
            return false;
        }


    }

    private void remove(Node curr, E value) {
        if (curr == null) {
            elemIsDeleted = false;
            return;
        }

        int cmp = compare(curr.value, value); //curr.value.compareTo(value);
        if (cmp > 0)
            remove(curr.left, value);
        else if (cmp < 0)
            remove(curr.right, value);
        else if (cmp == 0) {

            if (curr.left == null && curr.right == null) {

                if (curr.father.left == curr)
                    curr.father.left = null;
                else curr.father.right = null;

                curr = null;
            }

            if (curr.left != null && curr.right == null) {
                if (curr.father.left == curr)
                    curr.father.left = curr.left;
                else curr.father.right = curr.left;

                curr.left.father = curr.father;
                curr = null;

            }
            if (curr.right != null && curr.left == null) {
                if (curr.father.left == curr)
                    curr.father.left = curr.right;
                else curr.father.right = curr.right;

                curr.right.father = curr.father;
                curr = null;

            }
            if (curr.left != null && curr.right != null) {
                Node temp = curr.right;
                while (temp.left != null)
                    temp = temp.left;

                if (temp.right == null) {
                    curr.value = temp.value;
                    temp = null;

                } else {
                    curr.value = temp.value;
                    temp.father.left = temp.right;
                    temp.right.father = temp.father;
                    temp = null;
                }

            }
        }

        int height = getHeight(curr.left) - getHeight(curr.right);
        if (height == 2)
            curr = rightRotation(curr);
        else if (height == -2)
            curr = leftRotation(curr);

        return;


    }

    /**
     * Ищет элемент с таким же значением в дереве.
     * Инвариант: на вход всегда приходит NotNull объект, который имеет корректный тип
     *
     * @param object элемент который необходимо поискать
     * @return true, если такой элемент содержится в дереве
     */
    private boolean containsElem = false;//Переменная устанавливается в true, если дерево содержит искомый элемент

    @Override
    public boolean contains(Object object) {
        @SuppressWarnings("unchecked")
        E value = (E) object;
        contains(root, value);
        if (containsElem) {
            containsElem = false;
            return true;
        }

        return false;

        //todo: следует реализовать
    }

    private void contains(Node curr, E value) {
        if (curr == null) {
            return;
        }
        int cmp = compare(curr.value,value);//curr.value.compareTo(value);
        if (cmp > 0) contains(curr.left, value);
        else if (cmp < 0) contains(curr.right, value);
        else {
            containsElem = true;
        }
        return;
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
        if (root == null)
            throw new NoSuchElementException("first");

        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.value;

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
        if (root == null)
            throw new NoSuchElementException("last");

        Node curr = root;
        while (curr.right != null)
            curr = curr.right;

        return curr.value;

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
        return size;
    }

    @Override
    public String toString() {
        return "AVLTree{" +
                "tree=" + root +
                "size=" + size + ", " +
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
     * Обходит дерево и проверяет что высоты двух поддеревьев
     * различны по высоте не более чем на 1
     */
    @Override
    public void checkBalanced() throws NotBalancedTreeException {

        traverseTreeAndCheckBalanced(root);
    }

    private Node leftRotation(Node curr) {
        int c = getHeight(curr.right.left);
        int r = getHeight(curr.right.right);
        if (c <= r) return leftSmallRotation(curr);
        else return leftBigRotation(curr);
    }


    private Node rightRotation(Node curr) {
        int l = getHeight(curr.left.left);
        int c = getHeight(curr.left.right);
        if (c <= l) return rightSmallRotation(curr);
        else return rightBigRotation(curr);
    }


    private Node leftSmallRotation(Node node) {
        if (node.father != null) {
            if (node.father.left == node)
                node.father.left = node.right;
            else node.father.right = node.right;

        }
        node.right.father = node.father;

        Node temp = node.right.left;
        node.father = node.right;
        node.right.left = node;
        node.right = temp;
        return node.father;
    }


    private Node leftBigRotation(Node node) {
        Node tempM = node.right.left.left;
        Node tempN = node.right.left.right;

        node.right.left.left = node;
        node.right.left.right = node.right;
        if (node.father != null) {
            if (node.father.left == node)
                node.father.left = node.right.left;
            else node.father.right = node.right.left;
        }

        node.right.left.father = node.father;


        node.right.father = node.right.left;
        node.right.left = tempN;

        node.father = node.right.father;
        if (tempN != null)
            tempN.father = node.right;
        node.right = tempM;
        if (tempM != null)
            tempM.father = node;
        return node.father;


    }

    private Node rightSmallRotation(Node node) {
        if (node.father != null) {
            if (node.father.left == node)
                node.father.left = node.left;
            else node.father.right = node.left;

            node.left.father = node.father;
        }

        Node temp = node.left.right;
        node.father = node.left;
        node.left.right = node;
        node.left = temp;
        return node.father;


    }

    private Node rightBigRotation(Node node) {
        Node tempM = node.left.right.left;
        Node tempN = node.left.right.right;

        node.left.right.right = node;
        node.left.right.left = node.left;

        if (node.father != null) {
            if (node.father.left == node)
                node.father.left = node.left.right;
            else node.father.right = node.left.right;
        }
        node.left.right.father = node.father;

        node.left.father = node.left.right;
        node.left.right = tempM;

        node.father = node.left.father;
        if (tempM != null)
            tempM.father = node.left;
        node.left = tempN;
        if (tempN != null)
            tempN.father = node;


        return node.father;

    }


    private int getHeight(Node node) {
        if (node == null) return 0;
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private int traverseTreeAndCheckBalanced(Node curr) throws NotBalancedTreeException {

        if (curr == null) {
            return 1;
        }
        int leftHeight = traverseTreeAndCheckBalanced(curr.left);
        int rightHeight = traverseTreeAndCheckBalanced(curr.right);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            throw NotBalancedTreeException.create("The heights of the two child subtrees of any node must be differ by at most one",
                    leftHeight, rightHeight, curr.toString());

        }
        return Math.max(leftHeight, rightHeight) + 1;
    }


}
