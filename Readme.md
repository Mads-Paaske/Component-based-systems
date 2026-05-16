# Running the Asteroids Game
## Step 1 — Start the Scoring Service

The scoring service must be started **before** the game, otherwise scores will not be saved (the game will still run fine without it).

Open a terminal in the project root and run:

```bash
cd ScoringService
mvn spring-boot:run
```

To verify it is running, open your browser and go to:
```
http://localhost:8080/scores
```

You should see `[]` (an empty list).

**Leave this terminal open** — closing it shuts down the service.

## Step 2 — Start the Game

Open a **second terminal** in the project root and run:

```bash
mvn clean install
mvn exec:exec
```
## Viewing Scores

While both programs are running, open your browser and visit:

| URL | Description |
|-----|-------------|
| `http://localhost:8080/scores` | All recorded scores |
| `http://localhost:8080/scores/top` | Top 5 scores |


**Scores disappear on restart:** This is expected — scores are stored in memory and do not persist between runs of the scoring service.