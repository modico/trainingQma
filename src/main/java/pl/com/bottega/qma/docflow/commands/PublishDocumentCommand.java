package pl.com.bottega.qma.docflow.commands;

import pl.com.bottega.qma.core.Command;

import java.util.HashSet;
import java.util.Set;

public class PublishDocumentCommand implements Command {

  public String documentNumber;
  public Set<String> departmentCodes = new HashSet<>();
  public Long publisherId;

}
