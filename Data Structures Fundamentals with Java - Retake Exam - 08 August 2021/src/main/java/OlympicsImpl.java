import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OlympicsImpl implements Olympics {

    private final Map<Integer, Competition> competitions;
    private final Map<String, Competitor> competitors;

    public OlympicsImpl() {
        this.competitors = new LinkedHashMap<>();
        this.competitions = new HashMap<>();
    }


    @Override
    public void addCompetitor(int id, String name) {
        if (this.competitors.containsKey(String.valueOf(id))) {
            throw new IllegalArgumentException();
        }
        this.competitors.put(String.valueOf(id), new Competitor(id, name));
    }

    @Override
    public void addCompetition(int id, String name, int score) {
        if (this.competitions.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        this.competitions.put(id, new Competition(name, id, score));
    }

    @Override
    public void compete(int competitorId, int competitionId) {
        Competitor competitor = this.getCompetitor(competitorId);
        Competition competition = this.getCompetition(competitionId);

        competition.getCompetitors().add(competitor);
        int competitionScore = competition.getScore();
        long competitorTotalScore = competitor.getTotalScore();
        competitor.setTotalScore(competitorTotalScore + competitionScore);
    }

    @Override
    public void disqualify(int competitionId, int competitorId) {
        Competition competition = this.getCompetition(competitionId);
        Competitor competitor = this.getCompetitor(competitorId);

        if (!competition.getCompetitors().contains(competitor)) {
            throw new IllegalArgumentException();
        }

        int competitionScore = competition.getScore();
        competition.getCompetitors().remove(competitor);
        long competitorTotalScore = competitor.getTotalScore();
        competitor.setTotalScore(competitorTotalScore - competitionScore);
    }

    @Override
    public Iterable<Competitor> findCompetitorsInRange(long min, long max) {
        return this.competitors
                .values()
                .stream()
                .filter(c -> c.getTotalScore() > min && c.getTotalScore() <= max)
                .sorted(Competitor::compareTo)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Competitor> getByName(String name) {
        List<Competitor> list = this.competitors.values()
                .stream()
                .filter(c -> c.getName().equals(name))
                .sorted(Competitor::compareTo)
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return list;
    }

    @Override
    public Iterable<Competitor> searchWithNameLength(int minLength, int maxLength) {
        return this.competitors.values()
                .stream()
                .filter(c -> c.getName().length() >= minLength && c.getName().length() <= maxLength)
                .sorted(Competitor::compareTo)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean contains(int competitionId, Competitor comp) {
        Competition competition = this.getCompetition(competitionId);
        return competition.getCompetitors().contains(comp);
    }

    @Override
    public int competitionsCount() {
        return this.competitions.size();
    }

    @Override
    public int competitorsCount() {
        return this.competitors.size();
    }

    @Override
    public Competition getCompetition(int id) {
        if (!this.competitions.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        return this.competitions.get(id);
    }

    private Competitor getCompetitor(int id) {
        if (!this.competitors.containsKey(String.valueOf(id))) {
            throw new IllegalArgumentException();
        }
        return this.competitors.get(String.valueOf(id));
    }
}
