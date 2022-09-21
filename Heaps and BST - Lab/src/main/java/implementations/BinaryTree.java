package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTree<E> implements AbstractBinaryTree<E> {

    private E key;
    private final BinaryTree<E> right;
    private final BinaryTree<E> left;

    public BinaryTree(E key, BinaryTree<E> right, BinaryTree<E> left) {
        this.key = key;
        this.right = right;
        this.left = left;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return this.left;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return this.right;
    }

    @Override
    public void setKey(E key) {
        this.key = key;
    }

    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder builder = new StringBuilder();
        String padding = createPadding(indent);
        builder.append(padding)
                .append(this.getKey());
        if (this.getRight() != null) {
            builder.append(System.lineSeparator())
                    .append(this.getRight().asIndentedPreOrder(indent + 2));
        }
        if (this.getLeft() != null) {
            builder.append(System.lineSeparator())
                    .append(this.getLeft().asIndentedPreOrder(indent + 2));
        }
        return builder.toString();
    }

    private String createPadding(int indent) {
        StringBuilder padding = new StringBuilder();
        // not using String.repeat() for compatibility with "judge"
        for (int i = 0; i < indent; i++) {
            padding.append(" ");
        }
        return padding.toString();
    }

    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        ArrayList<AbstractBinaryTree<E>> list = new ArrayList<>();
        list.add(this);
        if (this.getRight() != null) {
            list.addAll(this.getRight().preOrder());
        }
        if (this.getLeft() != null) {
            list.addAll(this.getLeft().preOrder());
        }
        return list;
    }

    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        ArrayList<AbstractBinaryTree<E>> list = new ArrayList<>();
        if (this.getRight() != null) {
            list.addAll(this.getRight().inOrder());
        }
        list.add(this);
        if (this.getLeft() != null) {
            list.addAll(this.getLeft().inOrder());
        }
        return list;
    }

    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        ArrayList<AbstractBinaryTree<E>> list = new ArrayList<>();
        if (this.getRight() != null) {
            list.addAll(this.getRight().postOrder());
        }
        if (this.getLeft() != null) {
            list.addAll(this.getLeft().postOrder());
        }
        list.add(this);
        return list;
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {
        if (this.getRight() != null) {
            this.getRight().forEachInOrder(consumer);
        }
        consumer.accept(this.getKey());
        if (this.getLeft() != null) {
            this.getLeft().forEachInOrder(consumer);
        }
    }
}
