package pl.com.bottega.qma.confirmation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.bottega.qma.confirmation.handlers.ConfirmDocumentHandler;
import pl.com.bottega.qma.docflow.DocumentRepository;
import pl.com.bottega.qma.hr.HrFacade;

@Configuration
public class ConfirmationConfig {

  @Bean
  public ConfirmDocumentHandler confirmDocumentHandler(ConfirmationRepository confirmationRepository, DocflowFacade docflowFacade) {
    return new ConfirmDocumentHandler(confirmationRepository, docflowFacade);
  }

  @Bean
  public ConfirmationRepository confirmationRepository() {
    return new InMemoryConfirmationRepository();
  }

  @Bean
  public DocflowFacade docflowFacade(DocumentRepository documentRepository, HrFacade hrFacade) {
    return new DefaultDocflowFacade(documentRepository, hrFacade);
  }

}
