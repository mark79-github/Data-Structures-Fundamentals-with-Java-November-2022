package core;

import model.Message;
import shared.DataTransferSystem;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;

public class MessagingSystem implements DataTransferSystem {

    static class Node<E> {
        E key;
        Node<E> left;
        Node<E> right;

        public Node(E value) {
            key = value;
            left = right = null;
        }

        public E getKey() {
            return key;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }
    }

    private Node<Message> root = null;
    private int count = 0;

    @Override
    public void add(Message message) {
        if (FALSE.equals(this.contains(message))) {
            this.root = this.add(this.root, message);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Node<Message> add(Node<Message> node, Message message) {
        if (node == null) {
            this.count++;
            return new Node<>(message);
        } else {
            if (message.getWeight() < node.getKey().getWeight()) {
                node.left = add(node.left, message);
            } else {
                node.right = add(node.right, message);
            }
        }
        return node;
    }

    @Override
    public Message getByWeight(int weight) {
        this.root = getByWeight(this.root, weight);
        if (this.root != null) {
            return this.root.getKey();
        }
        throw new IllegalArgumentException();
    }

    private Node<Message> getByWeight(Node<Message> node, int weight) {
        if (node == null || node.getKey().getWeight() == weight) {
            return node;
        }
        if (node.getKey().getWeight() > weight) {
            return getByWeight(node.getLeft(), weight);
        }
        return getByWeight(node.getRight(), weight);
    }

    @Override
    public Message getLightest() {
        if (this.root == null) {
            throw new IllegalStateException();
        }
        Node<Message> lightestNode = this.getLightest(this.root);
        return lightestNode.getKey();
    }

    public Node<Message> getLightest(Node<Message> node) {
        if (node.getLeft() == null) {
            return node;
        }
        return getLightest(node.getLeft());
    }

    @Override
    public Message getHeaviest() {
        if (this.root == null) {
            throw new IllegalStateException();
        }
        Node<Message> heaviestNode = this.getHeaviest(this.root);
        return heaviestNode.getKey();
    }

    public Node<Message> getHeaviest(Node<Message> node) {
        if (node.getRight() == null) {
            return node;
        }
        return getHeaviest(node.getRight());
    }

    @Override
    public Message deleteLightest() {
        if (this.root == null) {
            throw new IllegalStateException();
        }

        if (getRoot().getLeft() == null) {
            Node<Message> temp = this.getRoot();
            this.root = this.root.getRight();
            this.count--;
            return temp.getKey();
        }

        Node<Message> current = getRoot();

        while (current.getLeft().getLeft() != null) {
            current = current.getLeft();
        }

        Node<Message> temp = current.getLeft();
        if (current.getLeft().getRight() != null) {
            current.left = current.getLeft().getRight();
        } else {
            current.left = null;
        }

        this.count--;
        return temp.getKey();
    }

    @Override
    public Message deleteHeaviest() {

        if (this.root == null) {
            throw new IllegalStateException();
        }

        if (getRoot().getRight() == null) {
            Node<Message> temp = this.getRoot();
            this.root = this.root.getLeft();
            this.count--;
            return temp.getKey();
        }

        Node<Message> current = getRoot();

        while (current.getRight().getRight() != null) {
            current = current.getRight();
        }

        Node<Message> temp = current.getRight();
        if (current.getRight().getLeft() != null) {
            current.right = current.getRight().getLeft();
        } else {
            current.right = null;
        }

        this.count--;
        return temp.getKey();
    }

    @Override
    public Boolean contains(Message message) {
        Node<Message> contains = contains(this.root, message);
        return contains != null;
    }

    private Node<Message> contains(Node<Message> node, Message message) {
        if (node == null || node.getKey().getWeight() == message.getWeight()) {
            return node;
        }
        if (node.getKey().getWeight() > message.getWeight()) {
            return contains(node.getLeft(), message);
        }
        return contains(node.getRight(), message);
    }

    @Override
    public List<Message> getOrderedByWeight() {
        return this.getInOrder();
    }

    @Override
    public List<Message> getPostOrder() {
        List<Message> list = new ArrayList<>();
        getPostOrder(this.root, list);
        return list;
    }

    private void getPostOrder(Node<Message> node, List<Message> messages) {
        if (node != null) {
            getPostOrder(node.getLeft(), messages);
            getPostOrder(node.getRight(), messages);
            messages.add(node.getKey());
        }
    }

    @Override
    public List<Message> getPreOrder() {
        List<Message> list = new ArrayList<>();
        getPreOrder(this.root, list);
        return list;
    }

    private void getPreOrder(Node<Message> node, List<Message> messages) {
        if (node != null) {
            messages.add(node.getKey());
            getPreOrder(node.getLeft(), messages);
            getPreOrder(node.getRight(), messages);
        }
    }

    @Override
    public List<Message> getInOrder() {
        List<Message> list = new ArrayList<>();
        getInOrder(this.root, list);
        return list;
    }

    private void getInOrder(Node<Message> node, List<Message> messages) {
        if (node != null) {
            getInOrder(node.getLeft(), messages);
            messages.add(node.getKey());
            getInOrder(node.getRight(), messages);
        }
    }

    @Override
    public int size() {
        return this.count;
    }

    private int height(Node<Message> node) {
        if (node == null) {
            return -1;
        }
        return Math.max(height(node.getLeft()), height(node.getRight())) + 1;
    }

    public Node<Message> getRoot() {
        return this.root;
    }
}