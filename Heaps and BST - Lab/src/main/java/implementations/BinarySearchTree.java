package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {

    private Node<E> root;

    public BinarySearchTree() {
        this.root = null;
    }

    public BinarySearchTree(Node<E> root) {
        this.root = root;
    }

    @Override
    public void insert(E element) {
        Node<E> current = this.getRoot();
        this.root = insert(current, element);
    }

    private Node<E> insert(Node<E> node, E element) {
        if (node == null) {
            return new Node<>(element);
        }

        if (node.value.compareTo(element) > 0)
            node.leftChild = insert(node.leftChild, element);
        else {
            node.rightChild = insert(node.rightChild, element);
        }

        return node;
    }

    @Override
    public boolean contains(E element) {
        return this.search(element) != null;
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {
        Node<E> node = this.search(this.getRoot(), element);
        return node == null ? null : new BinarySearchTree<>(node);
    }

    private Node<E> search(Node<E> current, E element) {
        if (current == null) {
            return null;
        }

        if (current.value.compareTo(element) < 0) {
            return search(current.rightChild, element);
        } else if (current.value.compareTo(element) > 0) {
            return search(current.leftChild, element);
        } else {
            return current;
        }
    }

    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.root.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.root.rightChild;
    }

    @Override
    public E getValue() {
        return this.root.value;
    }
}
