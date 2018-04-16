package pl.com.bottega.qma.docflow.handlers;

import pl.com.bottega.qma.core.Handler;
import pl.com.bottega.qma.docflow.DocumentRepository;
import pl.com.bottega.qma.docflow.commands.PublishDocumentCommand;

public class PublishDocumentHandler implements Handler<PublishDocumentCommand, Void> {

  private final DocumentRepository documentRepository;

  public PublishDocumentHandler(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  @Override
  public Void handle(PublishDocumentCommand publishDocumentCommand) {
    var doc = documentRepository.get(publishDocumentCommand.documentNumber);
    doc.publish(publishDocumentCommand);
    documentRepository.put(doc);
    return null;
  }
}
