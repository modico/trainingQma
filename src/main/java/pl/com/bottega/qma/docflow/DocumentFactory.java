package pl.com.bottega.qma.docflow;

import pl.com.bottega.qma.docflow.commands.CreateDocumentCommand;

public class DocumentFactory {

  private final DocumentNumberGenerator generator;

  public DocumentFactory(DocumentNumberGenerator generator) {
    this.generator = generator;
  }

  public Document create(CreateDocumentCommand cmd) {
    return new Document(generator.generate(), cmd);
  }

}
