package pl.com.bottega.qma.confirmation;

public interface ConfirmationRepository {

  void put(Confirmation confirmation);

  boolean contains(String documentNumber, Long confirmerId);

}
