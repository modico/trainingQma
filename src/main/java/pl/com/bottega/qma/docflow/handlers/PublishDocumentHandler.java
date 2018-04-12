package pl.com.bottega.qma.docflow.handlers;

import pl.com.bottega.qma.core.Handler;
import pl.com.bottega.qma.docflow.commands.PublishDocumentCommand;

public class PublishDocumentHandler implements Handler<PublishDocumentCommand, Void> {
  @Override
  public Void handle(PublishDocumentCommand publishDocumentCommand) {
    return null;
  }
}
