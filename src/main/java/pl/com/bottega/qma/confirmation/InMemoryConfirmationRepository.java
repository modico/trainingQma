package pl.com.bottega.qma.confirmation;

import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.Map;

public class InMemoryConfirmationRepository implements ConfirmationRepository {

  private Map<Key, Confirmation> db = new HashMap<>();

  @Override
  public void put(Confirmation confirmation) {
    db.put(new Key(confirmation), confirmation);
  }

  @Override
  public boolean contains(String documentNumber, Long confirmerId) {
    return db.containsKey(new Key(documentNumber, confirmerId));
  }

  private static class Key {
    String documentNumber;
    Long confirmerId;

    public Key(Confirmation confirmation) {
      documentNumber = confirmation.documentNumber();
      confirmerId = confirmation.confirmerId();
    }

    public Key(String documentNumber, Long confirmerId) {
      this.documentNumber = documentNumber;
      this.confirmerId = confirmerId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Key key = (Key) o;
      return Objects.equal(documentNumber, key.documentNumber) &&
          Objects.equal(confirmerId, key.confirmerId);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(documentNumber, confirmerId);
    }
  }

}
