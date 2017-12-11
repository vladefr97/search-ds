package ru.mail.polis;

public interface BalancedBinarySearchTree {

    /**
     * Обходит дерево и проверяет сбалансированность
     * @throws NotBalancedTreeException если дерево не сбалансированное с указанием дефектного узла
     */
    void checkBalanced() throws NotBalancedTreeException;
}
