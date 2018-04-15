package pl.com.bottega.qma.docflow.events;

import pl.com.bottega.qma.docflow.DocumentStatus;

import java.time.LocalDateTime;
import java.util.Set;

public class DocumentPublished extends DocumentEvent {
  public final Set<String> departments;

  public DocumentPublished(String number, Long employeeId, LocalDateTime timestamp, Set<String> departments) {
    super(number, employeeId, timestamp);
    this.departments = departments;
  }

  @Override
  public DocumentStatus targetStatus() {
    return DocumentStatus.PUBLISHED;
  }
}
