package dk.sdu.cbse.collision;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ScoringClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String scoringServiceUrl = "http://localhost:8080/scores";

    public void reportScore(String playerName, int points) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = String.format(
                    "{\"playerName\":\"%s\",\"score\":%d}",
                    playerName, points
            );

            HttpEntity<String> request = new HttpEntity<>(body, headers);
            restTemplate.postForObject(scoringServiceUrl, request, String.class);

        } catch (Exception e) {
            // If the scoring service is down, the game should still work
            System.out.println("Scoring service unavailable: " + e.getMessage());
        }
    }
}