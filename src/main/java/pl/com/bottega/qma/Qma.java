package pl.com.bottega.qma;

import pl.com.bottega.qma.confirmation.commands.ConfirmDocumentCommand;
import pl.com.bottega.qma.confirmation.commands.ConfirmDocumentOnBehalfCommand;
import pl.com.bottega.qma.confirmation.handlers.ConfirmDocumentHandler;
import pl.com.bottega.qma.core.CommandGateway;
import pl.com.bottega.qma.core.CommandLogger;
import pl.com.bottega.qma.core.SecurityManager;
import pl.com.bottega.qma.core.TxManager;
import pl.com.bottega.qma.docflow.commands.CreateDocumentCommand;
import pl.com.bottega.qma.docflow.handlers.CreateDocumentHandler;

public class Qma {

  public static void main(String[] args) {
    CommandGateway commandGateway = new CommandGateway(new CommandLogger(), new SecurityManager(), new TxManager());
    commandGateway.registerHandler(CreateDocumentCommand.class, new CreateDocumentHandler());
    ConfirmDocumentHandler confirmDocumentHandler = new ConfirmDocumentHandler();
    commandGateway.registerHandler(ConfirmDocumentCommand.class, confirmDocumentHandler::confirm);
    commandGateway.registerHandler(ConfirmDocumentOnBehalfCommand.class, confirmDocumentHandler::confirmOnBehalf);
  }

}
