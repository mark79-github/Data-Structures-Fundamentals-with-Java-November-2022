import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;

    public static class Node<E> {
        private final E value;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private int count;

        public Node(E value) {
            this.value = value;
            this.count = 1;
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public E getValue() {
            return this.value;
        }

        public int getCount() {
            return this.count;
        }
    }

    public BinarySearchTree() {
    }

    public BinarySearchTree(Node<E> root) {
        this.root = root;
    }

    public Node<E> getRoot() {
        return this.root;
    }

    public void eachInOrder(Consumer<E> consumer) {

        nodeInOrder(this.root, consumer);
    }

    public void insert(E element) {
        Node<E> newNode = new Node<>(element);

        if (this.root == null) {
            this.root = newNode;
        } else {
            insertInto(this.root, element);
        }
    }

    public boolean contains(E element) {
        Node<E> current = this.root;

        while (current != null) {
            if (isLess(element, current)) {
                current = current.leftChild;
            } else if (isGreater(element, current)) {
                current = current.rightChild;
            } else if (isEqual(element, current)) {
                return true;
            }
        }

        return false;
    }

    public BinarySearchTree<E> search(E element) {
        BinarySearchTree<E> result = new BinarySearchTree<>();
        Node<E> current = this.root;

        while (current != null) {
            if (isLess(element, current)) {
                current = current.leftChild;
            } else if (isGreater(element, current)) {
                current = current.rightChild;
            } else if (isEqual(element, current)) {
                return new BinarySearchTree<>();
            }
        }

        return result;
    }

    public List<E> range(E first, E second) {
        List<E> result = new ArrayList<>();

        Deque<Node<E>> deque = new ArrayDeque<>();
        deque.offer(this.root);

        while (!deque.isEmpty()) {
            Node<E> current = deque.poll();

            if (current.getLeft() != null) {
                deque.offer(current.leftChild);
            }

            if (current.getRight() != null) {
                deque.offer(current.rightChild);
            }

            if (isGreater(first, current) && isLess(second, current)) {
                result.add(current.getValue());
            } else if (isEqual(first, current) || isEqual(second, current)) {
                result.add(current.getValue());
            }
        }

        return result;
    }

    public void deleteMin() {
        ensureNonEmpty();

        if (this.root.getLeft() == null) {
            this.root = this.root.getRight();
            return;
        }

        Node<E> current = this.root;

        while (current.getLeft().getLeft() != null) {
            current.count--;
            current = current.getLeft();
        }

        current.count--;
        current.leftChild = current.getLeft().getRight();
    }

    public void deleteMax() {
        ensureNonEmpty();

        if (this.root.getRight() == null) {
            this.root = this.root.getLeft();
            return;
        }

        Node<E> current = this.root;

        while (current.getRight().getRight() != null) {
            current.count--;
            current = current.getRight();
        }

        current.count--;
        current.rightChild = current.getRight().getLeft();
    }

    public int count() {
        return this.root == null ? 0 : this.root.count;
    }

    public int rank(E element) {
        return nodeRank(this.root, element);
    }

    private int nodeRank(Node<E> node, E element) {
        if (node == null) {
            return 0;
        }

        if (isLess(element, node)) {
            return nodeRank(node.getLeft(), element);
        } else if (isEqual(element, node)) {
            return getNodeCount(node.getLeft());
        }

        return getNodeCount(node.getLeft()) + 1 + nodeRank(node.getRight(), element);
    }

    public E floor(E element) {
        if (this.root == null) {
            return null;
        }

        Node<E> current = this.root;
        Node<E> nearestValue = null;

        while (current != null) {
            if (isGreater(element, current)) {
                nearestValue = current;
                current = current.getRight();
            } else if (isLess(element, current)) {
                current = current.getLeft();
            } else {
                Node<E> left = current.getLeft();

                if (left != null && nearestValue != null) {
                    nearestValue = isGreater(left.getValue(), nearestValue) ? left : nearestValue;
                } else if (nearestValue == null) {
                    nearestValue = left;
                }
                break;
            }
        }
        return nearestValue == null ? null : nearestValue.value;
    }

    public E ceil(E element) {
        if (this.root == null) {
            return null;
        }

        Node<E> current = this.root;
        Node<E> nearestValue = null;

        while (current != null) {
            if (isLess(element, current)) {
                nearestValue = current;
                current = current.getLeft();
            } else if (isGreater(element, current)) {
                current = current.rightChild;
            } else {
                Node<E> right = current.getRight();

                if (right != null && nearestValue != null) {
                    nearestValue = isLess(right.getValue(), nearestValue) ? right : nearestValue;
                } else if (nearestValue == null) {
                    nearestValue = right;
                }
                break;
            }
        }
        return nearestValue == null ? null : nearestValue.value;
    }

    private void nodeInOrder(Node<E> node, Consumer<E> consumer) {
        if (this.root == null) {
            return;
        }

        nodeInOrder(node.getLeft(), consumer);
        consumer.accept(node.value);
        nodeInOrder(node.getRight(), consumer);
    }

    private boolean isLess(E element, Node<E> node) {
        return element.compareTo(node.getValue()) < 0;
    }

    private boolean isGreater(E element, Node<E> node) {
        return element.compareTo(node.getValue()) > 0;
    }

    private boolean isEqual(E element, Node<E> node) {
        return element.compareTo(node.getValue()) == 0;
    }

    private void insertInto(Node<E> node, E element) {
        if (isGreater(element, node)) {
            if (node.getRight() == null) {
                node.rightChild = new Node<>(element);
            } else {

                insertInto(node.getRight(), element);
            }
        } else if (isLess(element, node)) {
            if (node.getLeft() == null) {
                node.leftChild = new Node<>(element);
            } else {

                insertInto(node.getLeft(), element);
            }
        }
        node.count++;
    }

    private void ensureNonEmpty() {
        if (this.root == null) {
            throw new IllegalArgumentException();
        }
    }

    private int getNodeCount(Node<E> node) {
        return node == null ? 0 : node.getCount();
    }
}
