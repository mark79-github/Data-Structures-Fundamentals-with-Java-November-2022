import java.util.Comparator;

public class Competitor implements Comparator<Competitor>, Comparable<Competitor> {

    private int id;
    private String name;
    private long totalScore;

    public Competitor(int id, String name) {
        this.totalScore = 0;
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(long totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public int compare(Competitor o1, Competitor o2) {
        return o1.getId() - o2.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Competitor)) return false;

        Competitor that = (Competitor) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public int compareTo(Competitor o) {
        return this.getId() - o.getId();
    }
}
