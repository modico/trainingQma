package pl.com.bottega.qma.docflow.events;

import pl.com.bottega.qma.docflow.DocumentStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public class DocumentEdited extends DocumentEvent {

  public final Optional<String> title, content;

  public DocumentEdited(String number, Long employeeId, LocalDateTime timestamp, Optional<String> title, Optional<String> content) {
    super(number, employeeId, timestamp);
    this.title = title;
    this.content = content;
  }

  @Override
  public DocumentStatus targetStatus() {
    return DocumentStatus.DRAFT;
  }
}
