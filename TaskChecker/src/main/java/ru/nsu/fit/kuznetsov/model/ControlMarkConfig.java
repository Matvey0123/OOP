package ru.nsu.fit.kuznetsov.model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import java.util.Date;

@Data
public class ControlMarkConfig extends GroovyObjectSupport {

    private String mark;
    private Date date;

}
