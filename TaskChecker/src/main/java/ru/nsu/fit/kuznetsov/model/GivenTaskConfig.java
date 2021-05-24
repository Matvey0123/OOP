package ru.nsu.fit.kuznetsov.model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import java.util.Date;

@Data
public class GivenTaskConfig extends GroovyObjectSupport {

    private String id;
    private Date deadline;
    private String gradingId;
}
