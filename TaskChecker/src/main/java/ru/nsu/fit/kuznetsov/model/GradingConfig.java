package ru.nsu.fit.kuznetsov.model;
import lombok.Data;

import java.util.function.Function;

@Data
public class GradingConfig {
    private String id;
    private Function<EvaluatingParameters, Integer> evaluator;
}
