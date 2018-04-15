package pl.com.bottega.qma.confirmation;

import java.time.LocalDateTime;
import java.util.Optional;

public class Confirmation {

  private final String documentNumber;
  private final Long confirmerId;
  private final Optional<Long> managerId;
  private final LocalDateTime confirmedAt = LocalDateTime.now();

  public Confirmation(String documentNumber, Long confirmerId, Long managerId) {
    this(documentNumber, confirmerId, Optional.of(managerId));
  }

  public Confirmation(String documentNumber, Long confirmerId) {
    this(documentNumber, confirmerId, Optional.empty());
  }

  private Confirmation(String documentNumber, Long confirmerId, Optional<Long> managerId) {
    this.documentNumber = documentNumber;
    this.confirmerId = confirmerId;
    this.managerId = managerId;
  }

  public String documentNumber() {
    return documentNumber;
  }

  public Long confirmerId() {
    return confirmerId;
  }
}
