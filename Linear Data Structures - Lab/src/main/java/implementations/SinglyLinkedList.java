package implementations;

import interfaces.LinkedList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements LinkedList<E> {

    private static final int INITIAL_SIZE = 1;
    private Object[] elements;
    private int size;

    public SinglyLinkedList() {
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
    }

    @Override
    public void addFirst(E element) {
        if (this.size >= this.elements.length) {
            this.elements = grow();
        }
        shiftLeft();
        this.elements[0] = element;
        this.size++;
    }

    private Object[] grow() {
        return Arrays.copyOf(this.elements, this.elements.length * 2);
    }

    private void shiftLeft() {
        for (int i = this.size; i > 0; i--) {
            this.elements[i] = this.elements[i - 1];
        }
    }

    @Override
    public void addLast(E element) {
        if (this.size >= this.elements.length) {
            this.elements = grow();
        }
        this.elements[this.size++] = element;
    }

    @Override
    public E removeFirst() {
        E element = this.peekFirst();
        if (this.elements.length / 3 > this.size) {
            this.elements = shrink();
        }
        shiftRight();
        this.elements[this.size--] = null;
        return element;
    }

    private Object[] shrink() {
        return Arrays.copyOf(this.elements, this.elements.length / 2);
    }

    private void shiftRight() {
        for (int i = 0; i < this.size; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }

    @Override
    public E removeLast() {
        E element = this.peekLast();
        if (this.elements.length / 3 > this.size) {
            this.elements = shrink();
        }
        this.elements[this.size--] = null;
        return element;
    }

    @Override
    public E getFirst() {
        return peekFirst();
    }

    @Override
    public E getLast() {
        return peekLast();
    }

    @SuppressWarnings("unchecked")
    public E peekFirst() {
        if (this.size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return (E) this.elements[0];
    }

    @SuppressWarnings("unchecked")
    public E peekLast() {
        if (this.size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return (E) this.elements[this.size - 1];
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
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Object element = elements[currentIndex++];
                return (E) element;
            }
        };
    }
}
