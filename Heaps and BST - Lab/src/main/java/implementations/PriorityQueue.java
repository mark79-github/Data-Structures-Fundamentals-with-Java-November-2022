package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private final List<E> elements = new ArrayList<>();

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        if (size() == 0) {
            this.elements.add(element);
        } else {
            this.elements.add(element);
            for (int i = 0; i < size() - 1; i++) {
                if (less(getAt(i), element)) {
                    Collections.swap(this.elements, i, size() - 1);
                }
            }
        }
    }

    @Override
    public E peek() {
        checkIfEmpty();
        return this.elements.get(0);

    }

    private void checkIfEmpty() {
        if (size() == 0) {
            throw new IllegalStateException("Queue is empty");
        }
    }

    @Override
    public E poll() {
        checkIfEmpty();
        E element = this.elements.get(0);
        Collections.swap(this.elements, 0, size() - 1);
        this.elements.remove(size() - 1);
        this.heapifyDown(0);
        return element;
    }

    private E getAt(int index) {
        return this.elements.get(index);
    }

    private boolean less(E first, E second) {
        return first.compareTo(second) < 0;
    }

    private void heapifyDown(int index) {
        while (index < size() / 2) {
            int child = 2 * index + 1;
            if (child + 1 < size() && less(getAt(child), getAt(child + 1))) {
                child += 1;
            }
            if (less(getAt(child), getAt(index))) {
                break;
            }
            Collections.swap(this.elements, index, child);
            index = child;
        }
    }
}
