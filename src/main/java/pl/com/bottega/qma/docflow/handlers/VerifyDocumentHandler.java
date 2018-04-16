package pl.com.bottega.qma.docflow.handlers;

import pl.com.bottega.qma.core.Handler;
import pl.com.bottega.qma.docflow.DocumentRepository;
import pl.com.bottega.qma.docflow.commands.VerifyDocumentCommand;

public class VerifyDocumentHandler implements Handler<VerifyDocumentCommand, Void> {

  private final DocumentRepository documentRepository;

  public VerifyDocumentHandler(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  @Override
  public Void handle(VerifyDocumentCommand verifyDocumentCommand) {
    var doc = documentRepository.get(verifyDocumentCommand.documentNumber);
    doc.verify(verifyDocumentCommand);
    documentRepository.put(doc);
    return null;
  }
}
