package pl.com.bottega.qma.docflow.handlers;

import pl.com.bottega.qma.core.Handler;
import pl.com.bottega.qma.docflow.DocumentRepository;
import pl.com.bottega.qma.docflow.commands.ArchiveDocumentCommand;

public class ArchiveDocumentHandler implements Handler<ArchiveDocumentCommand, String> {

  private final DocumentRepository documentRepository;

  public ArchiveDocumentHandler(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  @Override
  public String handle(ArchiveDocumentCommand archiveDocumentCommand) {
    var doc = documentRepository.get(archiveDocumentCommand.documentNumber);
    doc.archive(archiveDocumentCommand);
    documentRepository.put(doc);
    return null;
  }
}
