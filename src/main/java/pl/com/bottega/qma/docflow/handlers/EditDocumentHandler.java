package pl.com.bottega.qma.docflow.handlers;

import pl.com.bottega.qma.core.Handler;
import pl.com.bottega.qma.docflow.commands.EditDocumentCommand;

public class EditDocumentHandler implements Handler<EditDocumentCommand, Void> {
  @Override
  public Void handle(EditDocumentCommand editDocumentCommand) {
    return null;
  }
}
