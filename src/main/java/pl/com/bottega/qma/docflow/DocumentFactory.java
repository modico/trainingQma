package pl.com.bottega.qma.docflow;

import pl.com.bottega.qma.core.events.EventPublisher;
import pl.com.bottega.qma.docflow.commands.CreateDocumentCommand;

public class DocumentFactory {

  private final DocumentNumberGenerator generator;
  private EventPublisher eventPublisher;

  public DocumentFactory(DocumentNumberGenerator generator, EventPublisher eventPublisher) {
    this.generator = generator;
    this.eventPublisher = eventPublisher;
  }

  public Document create(CreateDocumentCommand cmd) {
    return new Document(generator.generate(), cmd, eventPublisher);
  }

}
