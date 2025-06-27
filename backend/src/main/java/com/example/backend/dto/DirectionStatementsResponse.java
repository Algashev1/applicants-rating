package com.example.backend.dto;

import java.util.List;
import com.example.backend.model.Statement;

public class DirectionStatementsResponse {
    private List<Statement> current;
    private List<Statement> previous;
    private int newStatements;
    private int withdrawnStatements;

    public DirectionStatementsResponse() {}

    public DirectionStatementsResponse(List<Statement> current, List<Statement> previous, int newStatements, int withdrawnStatements) {
        this.current = current;
        this.previous = previous;
        this.newStatements = newStatements;
        this.withdrawnStatements = withdrawnStatements;
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

    public int getNewStatements() {
        return newStatements;
    }

    public void setNewStatements(int newStatements) {
        this.newStatements = newStatements;
    }

    public int getWithdrawnStatements() {
        return withdrawnStatements;
    }

    public void setWithdrawnStatements(int withdrawnStatements) {
        this.withdrawnStatements = withdrawnStatements;
    }
}