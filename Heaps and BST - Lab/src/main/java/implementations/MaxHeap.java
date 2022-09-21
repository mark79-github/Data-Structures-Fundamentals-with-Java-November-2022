package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {

    private final List<E> elements = new ArrayList<>();

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapUp(this.size() - 1);
    }

    private void heapUp(int index) {
        while (index > 0 && less(getParent(index), getCurrent(index))) {
            int parentAt = getParentIndex(index);
            Collections.swap(this.elements, parentAt, index);
            index = parentAt;
        }
    }

    private boolean less(E parent, E current) {
        return parent.compareTo(current) < 0;
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private E getCurrent(int index) {
        return this.elements.get(index);
    }

    private E getParent(int index) {
        int parentIndex = getParentIndex(index);
        return this.elements.get(parentIndex);
    }

    @Override
    public E peek() {
        if (this.size() == 0) {
            throw new IllegalStateException("Empty collection");
        }
        return this.elements.get(0);
    }
}
