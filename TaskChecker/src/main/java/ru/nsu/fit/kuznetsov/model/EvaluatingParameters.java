package ru.nsu.fit.kuznetsov.model;

import lombok.Data;

@Data
public class EvaluatingParameters {
    private int daysPastDeadline;
    private int pointsForTask;
    private boolean allTestsPassed;
}
