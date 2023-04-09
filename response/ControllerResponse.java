package response;

public class ControllerResponse {
  public static final int ERROR_STATUS = 0;
  public static final int SUCCESS_STATUS = 1;

  private int status;
  private String message;

  /**
   * Controller response in order to help controllers to have better contact with
   * views
   * 
   * @param status
   * @param message
   */
  public ControllerResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
