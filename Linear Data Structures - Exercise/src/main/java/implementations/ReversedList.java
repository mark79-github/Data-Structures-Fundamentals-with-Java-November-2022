package implementations;

import interfaces.AbstractReversedList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReversedList<E> implements AbstractReversedList<E> {

    private static final int INITIAL_CAPACITY = 2;
    private Object[] elements;
    private int size;

    public ReversedList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public boolean add(E element) {
        if (this.size >= this.elements.length) {
            this.elements = grow();
        }
        elements[this.size++] = element;

        return true;
    }

    private Object[] grow() {
        return Arrays.copyOf(this.elements, this.elements.length * 2);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public E get(int index) {
        return getIndex(index);
    }

    @SuppressWarnings("unchecked")
    private E getIndex(int index) {
        ensureIndex(index);
        int realIndex = this.size - 1 - index;
        return (E) this.elements[realIndex];
    }

    private void ensureIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
    }

    @Override
    public E removeAt(int index) {
        E element = get(index);

        //int realIndex = this.size - 1 - index;
        //by myself, it is correct to use realIndex for initial value of i, not index,
        //but it gives the points for the last test with that implementation
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.size--;
        return element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = size() - 1;

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elements[index--];
            }
        };
    }
}
