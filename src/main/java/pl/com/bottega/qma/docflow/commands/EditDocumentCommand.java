package pl.com.bottega.qma.docflow.commands;

import pl.com.bottega.qma.core.Command;

import java.util.Optional;

public class EditDocumentCommand implements Command {

  public String documentNumber;
  public Long editorId;
  public Optional<String> title, content;

}
