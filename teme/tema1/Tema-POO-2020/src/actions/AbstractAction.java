package actions;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;

public abstract class AbstractAction {
  private Input input;
  private ActionInputData actionInputData;
  private Writer fileWriter;
  private JSONArray arrayResult;

  public AbstractAction(
      Input input, ActionInputData actionInputData, Writer fileWriter, JSONArray arrayResult) {
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
