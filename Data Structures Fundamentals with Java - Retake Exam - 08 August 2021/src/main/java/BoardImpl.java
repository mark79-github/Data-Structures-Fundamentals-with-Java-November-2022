import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardImpl implements Board {

    private final Map<String, Card> cards;

    public BoardImpl() {
        this.cards = new HashMap<>();
    }

    @Override
    public void draw(Card card) {
        if (this.cards.containsKey(card.getName())) {
            throw new IllegalArgumentException();
        } else {
            this.cards.put(card.getName(), card);
        }
    }

    @Override
    public Boolean contains(String name) {
        return this.cards.containsKey(name);
    }

    @Override
    public int count() {
        return this.cards.size();
    }

    @Override
    public void play(String attackerCardName, String attackedCardName) {
        Card attackerCard;
        Card attackedCard;

        attackerCard = this.cards.get(attackerCardName);
        attackedCard = this.cards.get(attackedCardName);
        if (attackerCard == null || attackedCard == null) {
            throw new IllegalArgumentException();
        }

        if (attackerCard.getLevel() != attackedCard.getLevel()) {
            throw new IllegalArgumentException();
        }

        if (attackedCard.getHealth() <= 0) {
            return;
        }

        int damage = attackerCard.getDamage();
        int health = attackedCard.getHealth();
        if (damage >= health) {
            int score = attackerCard.getScore();
            attackerCard.setScore(score + attackedCard.getLevel());
        }
        attackedCard.setHealth(health - damage);
    }

    @Override
    public void remove(String name) {
        Card cardToRemove = this.cards.remove(name);
        if (cardToRemove == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeDeath() {
        this.cards.entrySet().removeIf(c -> c.getValue().getHealth() <= 0);
    }

    @Override
    public Iterable<Card> getBestInRange(int start, int end) {
        return this.cards.entrySet()
                .stream()
                .filter(c -> c.getValue().getScore() >= start && c.getValue().getScore() <= end)
                .sorted((f, s) -> s.getValue().getLevel() - f.getValue().getLevel())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Card> listCardsByPrefix(String prefix) {
        Comparator<Card> cardComparator = Comparator.comparing(Card::getReversedName).thenComparingInt(Card::getLevel);
        return this.cards.values()
                .stream()
                .filter(card -> card.getName().startsWith(prefix))
                .sorted(cardComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Card> searchByLevel(int level) {
        return this.cards.entrySet()
                .stream()
                .filter(c -> c.getValue().getLevel() == level)
                .sorted((f, s) -> s.getValue().getScore() - f.getValue().getScore())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public void heal(int health) {
        this.cards.values()
                .stream()
                .min(Comparator.comparingInt(Card::getHealth))
                .ifPresent(c -> c.setHealth(c.getHealth() + health));
    }
}
