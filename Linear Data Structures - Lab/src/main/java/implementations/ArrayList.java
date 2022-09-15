package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {

    private static final int INITIAL_SIZE = 4;
    private Object[] elements;
    private int size;

    public ArrayList() {
        this.elements = new Object[INITIAL_SIZE];
        this.size = 0;
    }

    @Override
    public boolean add(E element) {
        if (this.size >= this.elements.length) {
            Object[] resizedArr = new Object[this.elements.length * 2];
            System.arraycopy(this.elements, 0, resizedArr, 0, this.elements.length);
            this.elements = resizedArr;
        }
        elements[this.size++] = element;

        return true;
    }

    @Override
    public boolean add(int index, E element) {
        checkIndex(index);
        if (this.size == this.elements.length) {
            this.elements = grow();
        }
        for (int i = this.size; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[index] = element;
        this.size++;

        return true;
    }

    private Object[] grow() {
        return new Object[this.elements.length * 2];
    }

    private Object[] shrink() {
        return Arrays.copyOf(this.elements, this.elements.length / 2);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        E oldElement = this.get(index);
        this.elements[index] = element;
        return oldElement;
    }

    @Override
    public E remove(int index) {
        E element = this.get(index);
        this.set(index, null);

        if (this.size <= this.elements.length / 3) {
            this.elements = shrink();
        }

        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.size--;
        return element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        return this.indexOf(element) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && elements[currentIndex] != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(currentIndex++);
            }
        };
    }
}
