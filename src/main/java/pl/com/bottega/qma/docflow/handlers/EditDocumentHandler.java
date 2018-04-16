package pl.com.bottega.qma.docflow.handlers;

import pl.com.bottega.qma.core.Handler;
import pl.com.bottega.qma.docflow.DocumentRepository;
import pl.com.bottega.qma.docflow.commands.EditDocumentCommand;

public class EditDocumentHandler implements Handler<EditDocumentCommand, Void> {

  private final DocumentRepository documentRepository;

  public EditDocumentHandler(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  @Override
  public Void handle(EditDocumentCommand editDocumentCommand) {
    var doc = documentRepository.get(editDocumentCommand.documentNumber);
    doc.edit(editDocumentCommand);
    documentRepository.put(doc);
    return null;
  }
}
