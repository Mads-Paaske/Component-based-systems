package dk.sdu.cbse.scoringservice;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final List<ScoreRecord> scores = new ArrayList<>();

    @PostMapping
    public String addScore(@RequestBody ScoreRecord record) {
        scores.add(record);
        return "Score saved";
    }

    @GetMapping
    public List<ScoreRecord> getScores() {
        return scores;
    }

    @GetMapping("/top")
    public List<ScoreRecord> getTopScores() {
        return scores.stream()
                .sorted(Comparator.comparingInt(ScoreRecord::getScore).reversed())
                .limit(5)
                .toList();
    }
}