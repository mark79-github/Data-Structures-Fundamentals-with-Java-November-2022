package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {

    private final E element;
    private Tree<E> parent;
    private final List<Tree<E>> children;
    private int maxLevel = 0;
    private Tree<E> leftMostNode = null;

    public Tree(E key) {
        this.element = key;
        this.children = new ArrayList<>();
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.element;
    }

    @Override
    public String getAsString() {
        String result = dfs(this);
        return result.trim();
    }

    private String dfs(Tree<E> tree) {
        int level = 0;
        StringBuilder builder = new StringBuilder();
        dfs(tree, level, builder);
        return builder.toString();
    }

    private void dfs(Tree<E> tree, int level, StringBuilder builder) {
        // not using String.repeat() for compatibility with "judge"
        for (int i = 0; i < level; i++) {
            builder.append(" ");
        }
        if (level > maxLevel) {
            maxLevel = level;
            leftMostNode = tree;
        }
        builder.append(tree.getKey()).append(System.lineSeparator());
        for (Tree<E> child : tree.children) {
            this.dfs(child, level + 2, builder);
        }
    }

    @Override
    public List<E> getLeafKeys() {
        List<E> list = new ArrayList<>();

        Deque<Tree<E>> deque = new ArrayDeque<>();
        deque.offer(this);
        while (!deque.isEmpty()) {
            Tree<E> current = deque.poll();
            if (current.children.isEmpty()) {
                list.add(current.getKey());
            }
            for (Tree<E> child : current.children) {
                deque.offer(child);
            }
        }

        return list;
    }

    @Override
    public List<E> getMiddleKeys() {
        List<E> list = new ArrayList<>();

        Deque<Tree<E>> deque = new ArrayDeque<>();
        deque.offer(this);
        while (!deque.isEmpty()) {
            Tree<E> current = deque.poll();
            if (!current.children.isEmpty() && current.getParent() != null) {
                list.add(current.getKey());
            }
            for (Tree<E> child : current.children) {
                deque.offer(child);
            }
        }

        return list;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        dfs(this);
        return this.leftMostNode;
    }

    @Override
    public List<E> getLongestPath() {
        dfs(this);
        ArrayList<E> list = new ArrayList<>();
        Tree<E> current = this.leftMostNode;
        while (current.parent != null) {
            list.add(current.getKey());
            current = current.parent;
        }
        list.add(current.getKey());
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(final int sum) {
        List<List<E>> result = new ArrayList<>();
        depthFirstSearch(this, result);

        return result.stream()
                .filter(c -> sum == c.stream().map(String::valueOf).mapToInt(Integer::parseInt).sum())
                .collect(Collectors.toList());
    }

    private void depthFirstSearch(Tree<E> tree, List<List<E>> result) {
        for (Tree<E> child : tree.children) {
            depthFirstSearch(child, result);
        }
        if (tree.children.isEmpty()) {
            List<E> treeList = calculatePathWeight(tree);
            result.add(treeList);
        }
    }

    private List<E> calculatePathWeight(Tree<E> tree) {
        List<E> result = new ArrayList<>();
        result.add(tree.getKey());
        Tree<E> current = tree.parent;
        if (current != null) {
            while (current.parent != null) {
                result.add(0, current.getKey());
                current = current.parent;
            }
            result.add(0, current.getKey());
        }
        return result;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> result = new ArrayList<>();

        ArrayDeque<Tree<E>> deque = new ArrayDeque<>();
        deque.offer(this);
        while (!deque.isEmpty()) {
            Tree<E> current = deque.poll();
            int currentSum = pathWeight(current);
            if (currentSum == sum) {
                result.add(current);
            }
            for (Tree<E> child : current.children) {
                deque.offer(child);
            }
        }

        return result;
    }

    private int pathWeight(Tree<E> tree) {
        int result = 0;
        ArrayDeque<Tree<E>> deque = new ArrayDeque<>();
        deque.offer(tree);
        while (!deque.isEmpty()) {
            Tree<E> current = deque.poll();
            result += (int) current.getKey();
            for (Tree<E> child : current.children) {
                deque.offer(child);
            }
        }
        return result;
    }


}



