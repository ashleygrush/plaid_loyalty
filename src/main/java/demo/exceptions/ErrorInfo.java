package demo.exceptions;

public class ErrorInfo {

    StringBuffer url;
    String ex;

    public ErrorInfo(StringBuffer url, Exception ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
    }
}
