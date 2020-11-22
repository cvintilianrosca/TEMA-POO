package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;

public abstract class AbstractAction {
  private final Input input;
  private final ActionInputData actionInputData;
  private final Writer fileWriter;
  private final JSONArray arrayResult;

  public AbstractAction(
     final Input input, final ActionInputData actionInputData,
     final Writer fileWriter, final JSONArray arrayResult) {
    this.input = input;
    this.actionInputData = actionInputData;
    this.fileWriter = fileWriter;
    this.arrayResult = arrayResult;
  }

  public abstract StringBuilder executeCommand();

  public abstract void execute() throws IOException;

  public JSONArray getArrayResult() {
    return arrayResult;
  }

  public Writer getFileWriter() {
    return fileWriter;
  }

  public ActionInputData getActionInputData() {
    return actionInputData;
  }

  public Input getInput() {
    return input;
  }
}
