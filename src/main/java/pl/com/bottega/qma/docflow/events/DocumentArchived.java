package pl.com.bottega.qma.docflow.events;

import pl.com.bottega.qma.docflow.DocumentStatus;

import java.time.LocalDateTime;

public class DocumentArchived extends DocumentEvent {
  public DocumentArchived(String number, Long employeeId, LocalDateTime timestamp) {
    super(number, employeeId, timestamp);
  }

  @Override
  public DocumentStatus targetStatus() {
    return DocumentStatus.ARCHIVED;
  }
}
