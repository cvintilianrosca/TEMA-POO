
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
  /**
   * Function that  computes the result of query, recommendation, command
   *
   * <p>DO NOT MODIFY
   */
  public abstract StringBuilder executeCommand();
  /**
   * Function that writes the computed message to output
   *
   * <p>DO NOT MODIFY
   */
  public abstract void execute() throws IOException;

  /**
   * Function that returns arrayResult
   *
   * <p>DO NOT MODIFY
   */
  public JSONArray getArrayResult() {
    return arrayResult;
  }

  /**
   * Function that returns FileWriter
   *
   * <p>DO NOT MODIFY
   */
  public Writer getFileWriter() {
    return fileWriter;
  }

  /**
   * Function that returns Action data related
   *
   * <p>DO NOT MODIFY
   */
  public ActionInputData getActionInputData() {
    return actionInputData;
  }
  /**
   * Function that returns the database
   *
   * <p>DO NOT MODIFY
   */
  public Input getInput() {
    return input;
  }
}
