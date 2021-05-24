package ru.nsu.fit.kuznetsov.model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import java.util.Date;

@Data
public class LessonConfig extends GroovyObjectSupport {

    private Date lessonDate;

}
