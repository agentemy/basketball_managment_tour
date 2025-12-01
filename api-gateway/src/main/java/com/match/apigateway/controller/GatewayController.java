package com.match.apigateway.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class GatewayController {

    private final RestTemplate restTemplate;

    // Порты ваших сервисов
    private static final String PLAYER_SERVICE = "http://localhost:8081";
    private static final String TEAM_SERVICE = "http://localhost:8082";
    private static final String MATCH_SERVICE = "http://localhost:8083";

    public GatewayController() {
        this.restTemplate = new RestTemplate();
    }

    // Player Service routing
    @GetMapping("/players")
    public ResponseEntity<String> getPlayers() {
        return restTemplate.getForEntity(PLAYER_SERVICE + "/api/players", String.class);
    }

    @PostMapping("/players")
    public ResponseEntity<String> createPlayer(@RequestBody Object playerRequest) {
        return restTemplate.postForEntity(PLAYER_SERVICE + "/api/players", playerRequest, String.class);
    }

    // Team Service routing
    @GetMapping("/teams")
    public ResponseEntity<String> getTeams() {
        return restTemplate.getForEntity(TEAM_SERVICE + "/api/teams", String.class);
    }

    @PostMapping("/teams")
    public ResponseEntity<String> createTeam(@RequestBody Object teamRequest) {
        return restTemplate.postForEntity(TEAM_SERVICE + "/api/teams", teamRequest, String.class);
    }

    // Match Service routing
    @GetMapping("/matches")
    public ResponseEntity<String> getMatches() {
        return restTemplate.getForEntity(MATCH_SERVICE + "/api/matches", String.class);
    }

    @PostMapping("/matches")
    public ResponseEntity<String> createMatch(@RequestBody Object matchRequest) {
        return restTemplate.postForEntity(MATCH_SERVICE + "/api/matches", matchRequest, String.class);
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok(new HealthResponse("API Gateway is running"));
    }

    record HealthResponse(String status) {}
}