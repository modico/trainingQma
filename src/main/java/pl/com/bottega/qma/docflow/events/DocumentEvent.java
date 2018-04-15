package pl.com.bottega.qma.docflow.events;

import pl.com.bottega.qma.docflow.DocumentStatus;

import java.time.LocalDateTime;

public abstract class DocumentEvent {

  public final String number;
  public final Long employeeId;
  public final LocalDateTime timestamp;

  public DocumentEvent(String number, Long employeeId, LocalDateTime timestamp) {
    this.number = number;
    this.employeeId = employeeId;
    this.timestamp = timestamp;
  }

  public abstract DocumentStatus targetStatus();

}
