package com.example.backend.controller;

import com.example.backend.dto.DirectionStatementsResponse;
import com.example.backend.model.Statement;
import com.example.backend.service.StatementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statements")
public class StatementController {
    private final StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/by-personalNumber/{personalNumber}")
    public ResponseEntity<List<Statement>> getStatementsByPersonalNumber(@PathVariable String personalNumber) {
        List<Statement> statements = statementService.getStatementsByPersonalNumber(personalNumber);
        return ResponseEntity.ok(statements);
    }

    @GetMapping("/direction/{directionName}")
    public List<Statement> getStatementsForDirection(
            @PathVariable String directionName,
            @RequestParam(required = false, defaultValue = "false") boolean onlyPriorityOne
    ) {
        return statementService.getStatementsForDirection(directionName, onlyPriorityOne);
    }

    @GetMapping("/direction/{directionName}/with-previous")
    public Map<String, Object> getStatementsWithPrevious(
            @PathVariable String directionName,
            @RequestParam(required = false) boolean onlyPriorityOne,
            @RequestParam(required = false) String date
    ) {
        return statementService.getStatementsWithPrevious(directionName, onlyPriorityOne, date);
    }

    @GetMapping("/dates")
    public List<String> getAvailableDates() {
        return statementService.getAvailableDates();
    }

    @GetMapping("/direction/{instituteName}/{directionName}/stats")
    public DirectionStatementsResponse getDirectionStats(
            @PathVariable String instituteName,
            @PathVariable String directionName,
            @RequestParam(required = false) boolean onlyPriorityOne,
            @RequestParam(required = false) String date
    ) {
        return statementService.getDirectionStatementsResponse(instituteName, directionName, onlyPriorityOne, date);
    }
}