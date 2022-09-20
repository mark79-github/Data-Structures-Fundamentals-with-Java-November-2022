package implementations;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TreeFactory {
    private final Map<Integer, Tree<Integer>> nodesByKeys;

    public TreeFactory() {
        this.nodesByKeys = new LinkedHashMap<>();
    }

    public Tree<Integer> createTreeFromStrings(String[] input) {
        for (String s : input) {
            int[] intArray = Arrays.stream(s.split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int parent = intArray[0];
            int child = intArray[1];
            addEdge(parent, child);
        }
        return getRoot();
    }

    private Tree<Integer> getRoot() {
        for (Tree<Integer> tree : nodesByKeys.values()) {
            if (tree.getParent() == null) {
                return tree;
            }
        }
        return null;
    }

    public Tree<Integer> createNodeByKey(int key) {
        this.nodesByKeys.putIfAbsent(key, new Tree<>(key));
        return this.nodesByKeys.get(key);
    }

    public void addEdge(int parent, int child) {
        Tree<Integer> parentNode = createNodeByKey(parent);
        Tree<Integer> childNode = createNodeByKey(child);

        parentNode.addChild(childNode);
        childNode.setParent(parentNode);
    }
}



