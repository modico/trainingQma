package pl.com.bottega.qma.docflow.numbergenerators;

import pl.com.bottega.qma.docflow.DocumentNumberGenerator;

import java.util.UUID;

public class QEPNumberGenerator implements DocumentNumberGenerator {
  @Override
  public String generate() {
    return String.format("QEP/%s", UUID.randomUUID().toString());
  }
}
