package implementations;

import interfaces.LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        private final E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(E value) {
            this.element = value;
        }
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.head != null) {
            Node<E> current = this.head;
            current.prev = newNode;
            newNode.next = this.head;
            this.tail = current;
        }
        this.head = newNode;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.head == null) {
            this.head = this.tail = newNode;
        } else {
            Node<E> current = this.tail;
            current.next = newNode;
            newNode.prev = current;
            this.tail = newNode;
        }
        this.size++;
    }

    @Override
    public E removeFirst() {
        ensureNotEmpty();
        E element = this.head.element;
        if (this.size == 1) {
            this.head = this.tail = null;
        } else {
            Node<E> newHead = this.head.next;
            newHead.prev = null;
            this.head = newHead;
        }
        this.size--;
        return element;
    }

    private void ensureNotEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("Illegal remove for empty LinkedList");
        }
    }

    @Override
    public E removeLast() {
        ensureNotEmpty();
        if (this.size == 1) {
            return removeFirst();
        }

        E toReturn = this.tail.element;
        Node<E> element = this.tail.prev;
        element.next = null;
        this.tail = element;
        this.size--;

        return toReturn;
    }

    @Override
    public E getFirst() {
        ensureNotEmpty();
        return this.head.element;
    }

    @Override
    public E getLast() {
        Node<E> current = this.tail;
        return current.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E element = current.element;
                current = current.next;
                return element;
            }
        };
    }
}
