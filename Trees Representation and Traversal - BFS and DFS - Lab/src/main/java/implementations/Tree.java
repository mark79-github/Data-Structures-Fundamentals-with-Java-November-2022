package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Tree<E> implements AbstractTree<E> {

    private E key;
    private E parent;
    private final List<Tree<E>> children;

    @SafeVarargs
    public Tree(E key, Tree<E>... children) {
        this.key = key;
        this.children = new ArrayList<>();
        for (Tree<E> child : children) {
            child.parent = key;
            this.children.add(child);
        }
    }

    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();
        if (this.key == null) {
            return result;
        }

        ArrayDeque<Tree<E>> deque = new ArrayDeque<>();

        deque.offer(this);
        while (!deque.isEmpty()) {
            Tree<E> current = deque.poll();
            result.add(current.key);
            for (Tree<E> child : current.children) {
                deque.offer(child);
            }
        }

        return result;
    }

    @Override
    public List<E> orderDfs() {
        List<E> order = new ArrayList<>();
        this.dfs(this, order);
        return order;
    }

    private void dfs(Tree<E> tree, List<E> order) {
        for (Tree<E> child : tree.children) {
            this.dfs(child, order);
        }
        order.add(tree.key);
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parentNode = findNodeByKey(parentKey);
        if (parentNode != null) {
            parentNode.children.add(child);
        }
    }

    private Tree<E> findNodeByKey(E key) {
        ArrayDeque<Tree<E>> deque = new ArrayDeque<>();

        deque.offer(this);
        while (!deque.isEmpty()) {
            Tree<E> current = deque.poll();
            if (current.key.equals(key)) {
                return current;
            }
            for (Tree<E> child : current.children) {
                deque.offer(child);
            }
        }

        return null;
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> node = findNodeByKey(nodeKey);
        if (node != null) {
            Tree<E> parentNode = findNodeByKey(node.parent);
            if (parentNode != null) {
                parentNode.children.removeIf(child -> child.key.equals(nodeKey));
            } else {
                node.key = null;
            }
            node.parent = null;
            node.children.clear();
        }
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstNode = findNodeByKey(firstKey);
        Tree<E> secondNode = findNodeByKey(secondKey);

        if (firstNode == null || secondNode == null) {
            return;
        }

        Tree<E> firstParent = findNodeByKey(firstNode.parent);
        Tree<E> secondParent = findNodeByKey(secondNode.parent);

        if (firstParent == null) {
            firstNode.key = secondNode.key;
            firstNode.parent = null;
            firstNode.children.clear();
            firstNode.children.addAll(secondNode.children);
        } else if (secondParent == null) {
            secondNode.key = firstNode.key;
            secondNode.parent = null;
            secondNode.children.clear();
            secondNode.children.addAll(firstNode.children);
        } else {
            int firstNodeIndex = firstParent.children.indexOf(firstNode);
            int secondNodeIndex = secondParent.children.indexOf(secondNode);

            firstParent.children.set(firstNodeIndex, secondNode);
            secondParent.children.set(secondNodeIndex, firstNode);
            firstNode.parent = secondParent.key;
            secondNode.parent = firstParent.key;
        }
    }
}



