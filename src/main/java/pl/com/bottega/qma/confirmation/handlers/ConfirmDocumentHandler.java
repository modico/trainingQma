package pl.com.bottega.qma.confirmation.handlers;

import pl.com.bottega.qma.confirmation.Confirmation;
import pl.com.bottega.qma.confirmation.ConfirmationRepository;
import pl.com.bottega.qma.confirmation.DocflowFacade;
import pl.com.bottega.qma.confirmation.commands.ConfirmDocumentCommand;
import pl.com.bottega.qma.confirmation.commands.ConfirmDocumentOnBehalfCommand;

public class ConfirmDocumentHandler {
  
  private final ConfirmationRepository confirmationRepository;
  private final DocflowFacade docflowFacade;

  public ConfirmDocumentHandler(ConfirmationRepository confirmationRepository, DocflowFacade docflowFacade) {
    this.confirmationRepository = confirmationRepository;
    this.docflowFacade = docflowFacade;
  }

  public Void confirm(ConfirmDocumentCommand cmd) {
    ensureNotConfirmed(cmd.documentNumber, cmd.confirmerId);
    ensureDocumentPubishedFor(cmd.documentNumber, cmd.confirmerId);
    Confirmation confirmation = new Confirmation(cmd.documentNumber, cmd.confirmerId);
    confirmationRepository.put(confirmation);
    return null;
  }

  private void ensureDocumentPubishedFor(String documentNumber, Long confirmerId) {
    if(!docflowFacade.isPublishedFor(documentNumber, confirmerId)) {
      throw new InvalidConfirmationRequest();
    }
  }

  private void ensureNotConfirmed(String documentNumber, Long confirmerId) {
    if(confirmationRepository.contains(documentNumber, confirmerId)) {
      throw new InvalidConfirmationRequest();
    }
  }

  public Void confirmOnBehalf(ConfirmDocumentOnBehalfCommand cmd) {
    ensureNotConfirmed(cmd.documentNumber, cmd.confirmerId);
    ensureDocumentPubishedFor(cmd.documentNumber, cmd.confirmerId);
    Confirmation confirmation = new Confirmation(cmd.documentNumber, cmd.confirmerId);
    confirmationRepository.put(confirmation);
    return null;
  }

}
