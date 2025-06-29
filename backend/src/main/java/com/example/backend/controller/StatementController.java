package com.example.backend.controller;

import com.example.backend.dto.DirectionStatementsResponse;
import com.example.backend.dto.StatementPublicDto;
import com.example.backend.mapper.StatementMapper;
import com.example.backend.model.Statement;
import com.example.backend.service.StatementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/statements")
public class StatementController {
    private final StatementService statementService;
    private final StatementMapper statementMapper;

    public StatementController(StatementService statementService, StatementMapper statementMapper) {
        this.statementService = statementService;
        this.statementMapper = statementMapper;
    }

    @GetMapping("/by-personalNumber/{personalNumber}")
    public ResponseEntity<List<StatementPublicDto>> getStatementsByPersonalNumber(@PathVariable String personalNumber) {
        List<Statement> statements = statementService.getStatementsByPersonalNumber(personalNumber);
        return ResponseEntity.ok(statements.stream()
                                    .map(statementMapper::toPublicDto)
                                    .collect(Collectors.toList()));
    }

    @GetMapping("/direction/{directionName}")
    public List<StatementPublicDto> getStatementsForDirection(
            @PathVariable String directionName,
            @RequestParam(required = false, defaultValue = "false") boolean onlyPriorityOne
    ) {
        return statementService.getStatementsForDirection(directionName, onlyPriorityOne).stream()
                                                                .map(statementMapper::toPublicDto)
                                                                .collect(Collectors.toList());
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
}