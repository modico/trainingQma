package pl.com.bottega.qma.docflow;

public class IllegalDocumentOperation extends RuntimeException {

  public IllegalDocumentOperation(String message) {
    super(message);
  }
}
