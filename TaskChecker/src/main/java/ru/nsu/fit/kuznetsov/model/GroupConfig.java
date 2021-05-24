package ru.nsu.fit.kuznetsov.model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;

import java.util.List;

@Data
public class GroupConfig extends GroovyObjectSupport {

    private String name;
    List<StudentConfig> students;
    List<GivenTaskConfig> tasks;

}
