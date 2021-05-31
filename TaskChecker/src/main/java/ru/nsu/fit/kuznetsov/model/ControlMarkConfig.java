package ru.nsu.fit.kuznetsov.model;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ControlMarkConfig extends GroovyObjectSupport {

  private String name;
  private String date;
}
