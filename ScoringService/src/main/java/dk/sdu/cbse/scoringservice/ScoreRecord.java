package dk.sdu.cbse.scoringservice;

public class ScoreRecord {
    private String playerName;
    private int score;

    public ScoreRecord() {} // Jackson needs this to deserialize JSON

    public ScoreRecord(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() { return playerName; }
    public int getScore() { return score; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }
    public void setScore(int score) { this.score = score; }
}