package ru.nsu.fit.kuznetsov.model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class GivenTaskConfig extends GroovyObjectSupport {

  private String name;
  private String deadline;
  private String gradingId;
  private int points;
}
