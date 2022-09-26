package solutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BinaryTree {
    private final int value;
    private final BinaryTree left;
    private final BinaryTree right;

    public BinaryTree(int key, BinaryTree first, BinaryTree second) {
        this.value = key;
        this.left = first;
        this.right = second;
    }

    public Integer findLowestCommonAncestor(int first, int second) {
        List<Integer> firstPath = findPath(first);
        List<Integer> secondPath = findPath(second);

        int smallerSize = Math.min(firstPath.size(), secondPath.size());

        int i = 0;
        for (; i < smallerSize; i++) {
            if (!firstPath.get(i).equals(secondPath.get(i))) {
                break;
            }
        }
        return firstPath.get(i - 1);
    }

    public List<Integer> topView() {
        Map<Integer, Pair<Integer, Integer>> offsetToValueLevel = new HashMap<>();

        traverseTree(this, 0, 1, offsetToValueLevel);
        return offsetToValueLevel.values()
                .stream()
                .map(Pair::getKey)
                .collect(Collectors.toList());
    }

    private void traverseTree(BinaryTree tree, int offset, int level,
                              Map<Integer, Pair<Integer, Integer>> offsetToValueLevel) {
        if (tree == null) {
            return;
        }

        Pair<Integer, Integer> current = offsetToValueLevel.get(offset);

        if (current == null || level < current.getValue()) {
            offsetToValueLevel.put(offset, new Pair<>(tree.value, level));
        }

        traverseTree(tree.left, offset - 1, level + 1, offsetToValueLevel);
        traverseTree(tree.right, offset + 1, level + 1, offsetToValueLevel);

    }

    private List<Integer> findPath(int element) {
        List<Integer> result = new ArrayList<>();
        findNodePath(this, element, result);
        return result;
    }

    private boolean findNodePath(BinaryTree binaryTree, int element, List<Integer> currentPath) {
        if (binaryTree == null) {
            return false;
        }

        if (binaryTree.value == element) {
            return true;
        }

        currentPath.add(binaryTree.value);

        boolean leftResult = findNodePath(binaryTree.left, element, currentPath);
        if (leftResult) {
            return true;
        }

        boolean rightResult = findNodePath(binaryTree.right, element, currentPath);
        if (rightResult) {
            return true;
        }

        currentPath.remove(Integer.valueOf(binaryTree.value));
        return false;
    }
}
