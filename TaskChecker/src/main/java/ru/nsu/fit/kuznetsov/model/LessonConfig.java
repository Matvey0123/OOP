package ru.nsu.fit.kuznetsov.model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class LessonConfig extends GroovyObjectSupport {

  private String lessonDate;
}
