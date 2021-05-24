package ru.nsu.fit.kuznetsov.model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;

@Data
public class StudentConfig extends GroovyObjectSupport {

    //private String nickName;
    private String name;
    private String gitURL;

}
