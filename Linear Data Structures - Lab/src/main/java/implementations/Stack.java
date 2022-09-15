package implementations;

import interfaces.AbstractStack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<E> implements AbstractStack<E> {
    private static final int INITIAL_SIZE = 1;
    private Object[] elements;
    private int size;

    public Stack() {
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
    }

    @Override
    public void push(E element) {
        if (this.size >= this.elements.length) {
            this.elements = grow();
        }
        shiftLeft();
        this.elements[0] = element;
        this.size++;
    }

    private void shiftLeft() {
        for (int i = this.size; i > 0; i--) {
            this.elements[i] = this.elements[i - 1];
        }
    }

    private Object[] grow() {
        return Arrays.copyOf(this.elements, this.elements.length * 2);
    }

    private Object[] shrink() {
        return Arrays.copyOf(this.elements, this.elements.length / 2);
    }

    @Override
    public E pop() {
        E element = this.peek();
        if (this.elements.length / 3 > this.size) {
            this.elements = shrink();
        }
        shiftRight();
        this.elements[this.size] = null;
        this.size--;
        return element;
    }

    private void shiftRight() {
        for (int i = 0; i < this.size; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        if (this.size == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        return (E) this.elements[0];
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
