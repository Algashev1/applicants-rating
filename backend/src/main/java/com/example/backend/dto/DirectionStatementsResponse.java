package com.example.backend.dto;

import java.util.List;
import com.example.backend.model.Statement;

public class DirectionStatementsResponse {
    private List<Statement> current;
    private List<Statement> previous;

    public DirectionStatementsResponse() {}

    public DirectionStatementsResponse(List<Statement> current, List<Statement> previous) {
        this.current = current;
        this.previous = previous;
    }

    public List<Statement> getCurrent() {
        return current;
    }

    public void setCurrent(List<Statement> current) {
        this.current = current;
    }

    public List<Statement> getPrevious() {
        return previous;
    }

    public void setPrevious(List<Statement> previous) {
        this.previous = previous;
    }
}