package ru.nsu.fit.kuznetsov.notebook;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.cli.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class allows us to keep a notebook in JSON format and execute commands from command line.
 */
@JsonAutoDetect
public class Notebook {
  ArrayList<Note> list;

  /**
   * Creates new Notebook
   */
  public Notebook() {
    this.list = new ArrayList<>();
  }

  /**
   * Creates new Notebook using existed list
   * @param list the existed list of Notes
   */
  public Notebook(ArrayList<Note> list) {
    this.list = list;
  }

  /**
   * The class that contains struct of note in Notebook.
   */
  @JsonAutoDetect
  static class Note {
    @JsonProperty("name")
    public String name;

    @JsonProperty("note")
    public String note;

    @JsonProperty("time")
    public final String time;

    /**
     * Creates new Note
     * @param name the name of note
     * @param note the content of note
     * @param time the time of creating note
     */
    @JsonCreator
    public Note(
        @JsonProperty("name") String name,
        @JsonProperty("note") String note,
        @JsonProperty("time") String time) {
      this.name = name;
      this.note = note;
      this.time = time;
    }
  }

  /**
   * Adds specified note to the Notebook
   * @param args the array contains name and content of specified note
   */
  void addNote(String[] args) {
    LocalDateTime time = LocalDateTime.now();
    LocalDateTime toNote =
        LocalDateTime.of(
            time.getYear(),
            time.getMonth(),
            time.getDayOfMonth(),
            time.getHour(),
            time.getMinute(),
            time.getSecond());
    Note note = new Note(args[0], args[1], toNote.toString());
    list.add(note);
  }

  /**
   * Removes all notes from the Notebook which have the given name
   * @param name the name of notes to be deleted
   */
  void deleteNode(String name) {
    list.removeIf(elem -> elem.name.equals(name));
  }

  /**
   * Searches all notes in the Notebook which were created between two dates
   * And contains special keyWords in their names
   * @param from the lower date bound
   * @param to the upper date bound
   * @param keyWords the array of key words
   * @return the ArrayList of found notes
   */
  ArrayList<Note> search(LocalDateTime from, LocalDateTime to, String[] keyWords) {
    ArrayList<Note> result = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    for (Note elem : list) {
      LocalDateTime elemTime = LocalDateTime.parse(elem.time, formatter);
      if (elemTime.compareTo(from) >= 0 && elemTime.compareTo(to) <= 0) {
        for (String keyWord : keyWords) {
          if (elem.name.contains(keyWord)) {
            result.add(elem);
            break;
          }
        }
      }
    }
    return result;
  }

  /**
   * Creates options which can be presented in command line
   * @return the collection of created options
   */
  Options createOptions() {
    Options options = new Options();
    options.addOption(
        Option.builder("add").desc("Adds specified note to notebook").numberOfArgs(2).build());
    options.addOption("rm", true, "remove specified note");
    Option option = Option.builder("show").hasArgs().optionalArg(true).build();
    options.addOption(option);
    return options;
  }

  /**
   * Parses the command line arguments
   * @param options the options which can appear in arguments
   * @param args the arguments to be parsed
   * @return the parsed Commandline
   * @throws ParseException if arguments don't follows the rules defined for options
   */
  CommandLine parsing(Options options, String[] args) throws ParseException {
    CommandLineParser parser = new DefaultParser();
    return parser.parse(options, args);
  }

  /**
   * Executes arguments of Main function and updates the Notebook
   * @param file the JSON file to serialize the Notebook
   * @param args the arguments to execute
   * @return the String which contains a message if the arguments were executed successfully or not
   * @throws ParseException if arguments were not parsed correctly
   * @throws IOException if we don't have enough rules to read and write in the file
   */
  String executeCommandLine(File file, String[] args) throws ParseException, IOException {
    Options options = createOptions();
    CommandLine cmd = parsing(options, args);

    if (cmd.hasOption("add")) {
      String[] addArgs = cmd.getOptionValues("add");
      addNote(addArgs);
      writeToFile(file);
      return "\nThe note has been added successfully!\n";
    }
    if (cmd.hasOption("rm")) {
      deleteNode(cmd.getOptionValue("rm"));
      writeToFile(file);
      return "\nThe note has been removed successfully!\n";
    }
    if (cmd.hasOption("show")) {
      String[] showArgs = cmd.getOptionValues("show");
      if (showArgs == null) {
        for (Note note : list) {
          System.out.println("Name: " + note.name + "   Content: " + note.note);
        }
        return "\nThis is a list of notes sorted by add time!\n";
      } else {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        if (showArgs.length < 2) {
          throw new ParseException("Wrong number of arguments in option \"show\"!");
        }
        LocalDateTime from = LocalDateTime.parse(showArgs[0], formatter);
        LocalDateTime to = LocalDateTime.parse(showArgs[1], formatter);
        ArrayList<Note> notes =
            search(
                from.withSecond(0), to.withSecond(0), Arrays.copyOfRange(showArgs, 2, args.length));
        if (notes.isEmpty()) {
          return "\nNo notes in specified interval!\n";
        }
        for (Note note : notes) {
          System.out.println("Name: " + note.name + " Content: " + note.note);
        }
        return "\nThis is a list of dates in specified interval!\n";
      }
    }
    return "\nNo options\n";
  }

  /**
   * Writes content of the Notebook to JSON file using Jackson
   * @param file the JSON file
   * @throws IOException if we don't have enough rules to read and write in the file
   */
  void writeToFile(File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    writer.writeValue(file, list);
  }

  /**
   * Reads data from JSON file to the Notebook
   * @param file the JSON file
   * @return the Notebook read from the file
   * @throws IOException if we don't have enough rules to read and write in the file
   */
  static Notebook readFromFile(File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    ArrayList<Note> myNotes = new ArrayList<>(Arrays.asList(mapper.readValue(file, Note[].class)));
    return new Notebook(myNotes);
  }

  public static void main(String[] args) throws IOException {
    String fileSeparator = System.getProperty("file.separator");
    String path = "src" + fileSeparator + "notes.json";
    File file = new File(path);
    boolean isCreate = file.createNewFile();
    Notebook notebook;
    if (!isCreate) {
      notebook = readFromFile(file);
    } else {
      notebook = new Notebook();
    }
    try {
      String result = notebook.executeCommandLine(file, args);
      System.out.println(result);
    } catch (ParseException exception) {
      System.out.println(exception.getMessage());
    }
  }
}
