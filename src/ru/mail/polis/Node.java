package ru.mail.polis;

/**
 * Created by Nechaev Mikhail
 * Since 28/12/16.
 */
public class Node<E extends Comparable<E>> {

    public Node(E value) {
        this.value = value;
    }

    private E value;
    private Node<E> left;
    private Node<E> right;

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public Node<E> getLeft() {
        return left;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
    }

    public Node<E> getRight() {
        return right;
    }

    public void setRight(Node<E> right) {
        this.right = right;
    }

    //by @olerom
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        traverseTree(this, "", sb);
        return sb.toString();
    }

    private void traverseTree(Node<E> node, String indent, StringBuilder sb) {
        sb.append(node.value);
        sb.append("\n");
        if (node.right != null) {
            if (node.left == null) {
                appendChild(node.right, indent, sb);
                appendLastNullChild(indent, sb);
            } else {
                appendChild(node.right, indent, sb);
                appendLastChild(node.left, indent, sb);
            }
        } else if (node.left != null) {
            appendNullChild(indent, sb);
            appendLastChild(node.left, indent, sb);
        }
    }

    private void appendNullChild(String indent, StringBuilder sb) {
        sb.append(indent);
        sb.append('├');
        sb.append('─');
        sb.append("null\n");
    }

    private void appendLastNullChild(String indent, StringBuilder sb) {
        sb.append(indent);
        sb.append('└');
        sb.append('─');
        sb.append("null\n");
    }

    private void appendChild(Node<E> node, String indent, StringBuilder sb) {
        sb.append(indent);
        sb.append('├');
        sb.append('─');
        traverseTree(node, indent + "│ ", sb);
    }

    private void appendLastChild(Node<E> node, String indent, StringBuilder sb) {
        sb.append(indent);
        sb.append('└');
        sb.append('─');
        traverseTree(node, indent + "  ", sb);
    }
}
