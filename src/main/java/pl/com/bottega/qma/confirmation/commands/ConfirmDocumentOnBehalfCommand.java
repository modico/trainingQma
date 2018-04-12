package pl.com.bottega.qma.confirmation.commands;

import pl.com.bottega.qma.core.Command;

public class ConfirmDocumentOnBehalfCommand implements Command {

  public String documentNumber;
  public Long confirmerId;
  public Long targetEmployeeId;

}
