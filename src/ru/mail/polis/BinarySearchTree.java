package ru.mail.polis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class BinarySearchTree<E extends Comparable<E>> implements ISortedSet<E> {

    private Node<E> root;
    private int size;
    private final Comparator<E> comparator;

    public BinarySearchTree() {
        this.comparator = null;
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("set is empty, no first element");
        }
        Node<E> curr = root;
        while (curr.getLeft() != null) {
            curr = curr.getLeft();
        }
        return curr.getValue();
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("set is empty, no last element");
        }
        Node<E> curr = root;
        while (curr.getRight() != null) {
            curr = curr.getRight();
        }
        return curr.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(E value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (root != null) {
            Node<E> curr = root;
            while (curr != null) {
                int cmp = compare(curr.getValue(), value);
                if (cmp == 0) {
                    return true;
                } else if (cmp < 0) {
                    curr = curr.getRight();
                } else {
                    curr = curr.getLeft();
                }
            }
        }
        return false;
    }

    @Override
    public boolean add(E value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (root == null) {
            root = new Node<>(value);
        } else {
            Node<E> curr = root;
            while (true) {
                int cmp = compare(curr.getValue(), value);
                if (cmp == 0) {
                    return false;
                } else if (cmp < 0) {
                    if (curr.getRight() != null) {
                        curr = curr.getRight();
                    } else {
                        curr.setRight(new Node<>(value));
                        break;
                    }
                } else if (cmp > 0) {
                    if (curr.getLeft() != null) {
                        curr = curr.getLeft();
                    } else {
                        curr.setLeft(new Node<>(value));
                        break;
                    }
                }
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(E value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (root == null) {
            return false;
        }
        Node<E> parent = root;
        Node<E> curr = root;
        int cmp;
        while ((cmp = compare(curr.getValue(), value)) != 0) {
            parent = curr;
            if (cmp > 0) {
                curr = curr.getLeft();
            } else {
                curr = curr.getRight();
            }
            if (curr == null) {
                return false; // ничего не нашли
            }
        }
        if (curr.getLeft() != null && curr.getRight() != null) {
            Node<E> next = curr.getRight();
            Node<E> pNext = curr;
            while (next.getLeft() != null) {
                pNext = next;
                next = next.getLeft();
            } //next = наименьший из больших
            curr.setValue(next.getValue());
            next.setValue(null);
            //у правого поддерева нет левых потомков
            if (pNext == curr) {
                curr.setRight(next.getRight());
            } else {
                pNext.setLeft(next.getRight());
            }
            next.setRight(null);
        } else {
            if (curr.getLeft() != null) {
                reLink(parent, curr, curr.getLeft());
            } else if (curr.getRight() != null) {
                reLink(parent, curr, curr.getRight());
            } else {
                reLink(parent, curr, null);
            }
        }
        size--;
        return true;
    }

    private void reLink(Node<E> parent, Node<E> curr, Node<E> child) {
        if (parent == curr) {
            root = child;
        } else if (parent.getLeft() == curr) {
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
        curr.setValue(null);
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    @Override
    public List<E> inorderTraverse() {
        List<E> list = new ArrayList<E>(size);
        inorderTraverse(root, list);
        return list;
    }

    private void inorderTraverse(Node<E> curr, List<E> list) {
        if (curr == null) {
            return;
        }
        inorderTraverse(curr.getLeft(), list);
        list.add(curr.getValue());
        inorderTraverse(curr.getRight(), list);
    }

    @Override
    public Node<E> getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return "BST{" + root + "}";
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
//        Node<Integer> root = tree.getRoot();
//        if (root != null) {
//            root.print();
//        }
        tree.add(10);
        tree.add(5);
        tree.add(15);
        System.out.println(tree.inorderTraverse());
        System.out.println(tree.getRoot());
        System.out.println(tree.size);
        tree.remove(10);
        tree.remove(15);
        System.out.println(tree.size);
        System.out.println(tree.getRoot());
        tree.remove(5);
        System.out.println(tree.size);
        System.out.println(tree.getRoot());
        tree.add(15);
        System.out.println(tree.size);
        System.out.println(tree.getRoot());

        System.out.println("------------");
        Random rnd = new Random();
        tree = new BinarySearchTree<>();
        for (int i = 0; i < 15; i++) {
            tree.add(rnd.nextInt(50));
        }
        System.out.println(tree.inorderTraverse());
        System.out.println(tree.getRoot());
        tree = new BinarySearchTree<>((v1, v2) -> {
            // Even first
            final int c = Integer.compare(v1 % 2, v2 % 2);
            return c != 0 ? c : Integer.compare(v1, v2);
        });
        for (int i = 0; i < 15; i++) {
            tree.add(rnd.nextInt(50));
        }
        System.out.println(tree.inorderTraverse());
        System.out.println(tree.getRoot());
    }
}
