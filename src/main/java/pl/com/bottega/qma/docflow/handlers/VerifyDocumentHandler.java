package pl.com.bottega.qma.docflow.handlers;

import pl.com.bottega.qma.core.Handler;
import pl.com.bottega.qma.docflow.commands.VerifyDocumentCommand;

public class VerifyDocumentHandler implements Handler<VerifyDocumentCommand, Void> {
  @Override
  public Void handle(VerifyDocumentCommand verifyDocumentCommand) {
    return null;
  }
}
