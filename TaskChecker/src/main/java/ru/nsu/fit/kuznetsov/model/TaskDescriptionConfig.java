package ru.nsu.fit.kuznetsov.model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;

@Data
public class TaskDescriptionConfig extends GroovyObjectSupport {

    private String id;
    private String name;
    private int points;

}
