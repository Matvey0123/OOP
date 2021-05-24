package ru.nsu.fit.kuznetsov.model;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MainConfig extends GroovyObjectSupport {

    private final List<GroupConfig> groups = new ArrayList<>();
    private List<TaskDescriptionConfig> tasks;
    private final Map<String, GradingConfig> graders = new HashMap<>();

    public void group(Closure closure){
        GroupConfig groupConfig = new GroupConfig();
        closure.setDelegate(groupConfig);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        groups.add(groupConfig);
    }
    public void grader(Closure closure){
        GradingConfig gradingConfig = new GradingConfig();
        closure.setDelegate(gradingConfig);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        graders.put(gradingConfig.getId(), gradingConfig);
    }
}
