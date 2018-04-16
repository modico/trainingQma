package pl.com.bottega.qma.docflow.numbergenerators;

import pl.com.bottega.qma.docflow.DocumentNumberGenerator;

import java.time.LocalDateTime;

public class ISONumberGenerator implements DocumentNumberGenerator {
  @Override
  public String generate() {
    LocalDateTime time = LocalDateTime.now();
    return String.format("ISO/%d/%d/%s", time.getYear(), time.getMonth(), time.getDayOfMonth(),
        String.format("%x", (int) (Math.random() * 0xffffff)));
  }
}
