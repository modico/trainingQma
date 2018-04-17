package pl.com.bottega.qma;

import pl.com.bottega.qma.confirmation.DefaultDocflowFacade;
import pl.com.bottega.qma.confirmation.InMemoryConfirmationRepository;
import pl.com.bottega.qma.confirmation.commands.ConfirmDocumentCommand;
import pl.com.bottega.qma.confirmation.commands.ConfirmDocumentOnBehalfCommand;
import pl.com.bottega.qma.confirmation.handlers.ConfirmDocumentHandler;
import pl.com.bottega.qma.core.CommandGateway;
import pl.com.bottega.qma.core.CommandLogger;
import pl.com.bottega.qma.core.SecurityManager;
import pl.com.bottega.qma.core.TxManager;
import pl.com.bottega.qma.core.events.DefaultEventEngine;
import pl.com.bottega.qma.core.validation.ValidationEngine;
import pl.com.bottega.qma.docflow.DocumentFactory;
import pl.com.bottega.qma.docflow.InMemoryDocumentRepository;
import pl.com.bottega.qma.docflow.commands.CreateDocumentCommand;
import pl.com.bottega.qma.docflow.handlers.CreateDocumentHandler;
import pl.com.bottega.qma.docflow.numbergenerators.ISONumberGenerator;
import pl.com.bottega.qma.hr.RemoteHrFacade;

public class Qma {

  public static void main(String[] args) {
    CommandGateway commandGateway = new CommandGateway(new CommandLogger(), new SecurityManager(), new TxManager(), new ValidationEngine());
    InMemoryDocumentRepository documentRepository = new InMemoryDocumentRepository();
    commandGateway.registerHandler(CreateDocumentCommand.class, new CreateDocumentHandler(documentRepository, new DocumentFactory(new ISONumberGenerator(), new DefaultEventEngine())));
    ConfirmDocumentHandler confirmDocumentHandler = new ConfirmDocumentHandler(new InMemoryConfirmationRepository(), new DefaultDocflowFacade(documentRepository, new RemoteHrFacade()));
    commandGateway.registerHandler(ConfirmDocumentCommand.class, confirmDocumentHandler::confirm);
    commandGateway.registerHandler(ConfirmDocumentOnBehalfCommand.class, confirmDocumentHandler::confirmOnBehalf);
  }

}
