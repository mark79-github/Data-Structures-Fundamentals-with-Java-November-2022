package implementations;

import interfaces.Solvable;

public class BalancedParentheses implements Solvable {
    private final String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {

        DoublyLinkedList<Character> queue = new DoublyLinkedList<>();

        for (char ch : this.parentheses.toCharArray()) {
            if (ch == '{' || ch == '[' || ch == '(') {
                queue.addFirst(ch);
            } else {
                if (!queue.isEmpty()
                        && ((queue.getFirst() == '{' && ch == '}')
                        || (queue.getFirst() == '[' && ch == ']')
                        || (queue.getFirst() == '(' && ch == ')'))) {
                    queue.removeFirst();
                } else {
                    return false;
                }
            }
        }
        return queue.isEmpty();
    }
}
