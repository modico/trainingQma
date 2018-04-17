package pl.com.bottega.qma.confirmation;

import pl.com.bottega.qma.docflow.Document;
import pl.com.bottega.qma.docflow.DocumentRepository;
import pl.com.bottega.qma.hr.HrFacade;

import java.util.Set;

public class DefaultDocflowFacade implements DocflowFacade {

  private final DocumentRepository documentRepository;
  private HrFacade hrFacade;

  public DefaultDocflowFacade(DocumentRepository documentRepository, HrFacade hrFacade) {
    this.documentRepository = documentRepository;
    this.hrFacade = hrFacade;
  }

  @Override
  public boolean isPublishedFor(String documentNumber, Long employeeId) {
    Document document = documentRepository.get(documentNumber);
    Set<String> departments = document.publishedFor();
    return hrFacade.belongsToAnyDepratment(departments, employeeId);
  }
}
