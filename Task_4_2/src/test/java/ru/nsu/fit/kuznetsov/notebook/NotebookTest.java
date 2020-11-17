package ru.nsu.fit.kuznetsov.notebook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotebookTest {
    @Test public void test1() throws JsonProcessingException {
        Notebook notebook = new Notebook();
        String[] args = {"First note","My first note"};
        notebook.addNote(args);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        String result = writer.writeValueAsString(notebook.list);
        assertTrue(result.contains("First note"));
        assertTrue(result.contains("My first note"));
    }
    @Test public void test2(){
        Notebook notebook = new Notebook();
        String[] args = {"First note","My first note"};
        notebook.addNote(args);
        args[0] = "Second note"; args[1] = "Not first note";
        notebook.addNote(args);
        args[0] = "Second2 note"; args[1] = "I don't know what to write";
        notebook.addNote(args);
        notebook.deleteNode("Second note");
        Notebook correct = new Notebook();
        String[] correctArgs = {"First note","My first note"};
        correct.addNote(correctArgs);
        correct.addNote(args);
        for(int i =0;i<2;i++){
            assertEquals(correct.list.get(i).name,notebook.list.get(i).name);
            assertEquals(correct.list.get(i).note,notebook.list.get(i).note);
        }
    }
}