package pl.com.bottega.qma.docflow.handlers;

import pl.com.bottega.qma.core.Handler;
import pl.com.bottega.qma.docflow.DocumentFactory;
import pl.com.bottega.qma.docflow.DocumentRepository;
import pl.com.bottega.qma.docflow.commands.CreateDocumentCommand;

public class CreateDocumentHandler implements Handler<CreateDocumentCommand, String> {

  private final DocumentRepository documentRepository;
  private final DocumentFactory documentFactory;

  public CreateDocumentHandler(DocumentRepository documentRepository, DocumentFactory documentFactory) {
    this.documentRepository = documentRepository;
    this.documentFactory = documentFactory;
  }

  @Override
  public String handle(CreateDocumentCommand createDocumentCommand) {
    var doc = documentFactory.create(createDocumentCommand);
    documentRepository.put(doc);
    return doc.number();
  }
}


