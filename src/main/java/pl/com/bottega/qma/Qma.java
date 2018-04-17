package pl.com.bottega.qma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.com.bottega.qma.confirmation.commands.ConfirmDocumentCommand;
import pl.com.bottega.qma.confirmation.commands.ConfirmDocumentOnBehalfCommand;
import pl.com.bottega.qma.confirmation.handlers.ConfirmDocumentHandler;
import pl.com.bottega.qma.core.*;
import pl.com.bottega.qma.core.SecurityManager;
import pl.com.bottega.qma.core.events.DefaultEventEngine;
import pl.com.bottega.qma.core.validation.ValidationEngine;
import pl.com.bottega.qma.docflow.commands.CreateDocumentCommand;

import java.lang.reflect.ParameterizedType;

@SpringBootApplication
public class Qma {

  public static void main(String[] args) {
    SpringApplication.run(Qma.class, args);
  }

  @Bean
  public DefaultEventEngine defaultEventEngine() {
    return new DefaultEventEngine();
  }

  @Bean
  public CommandLogger commandLogger() {
    return new CommandLogger();
  }

  @Bean
  public SecurityManager securityManager() {
    return new SecurityManager();
  }

  @Bean
  public TxManager txManager() {
    return new TxManager();
  }

  @Bean
  public ValidationEngine validationEngine() {
    return new ValidationEngine();
  }

  @Bean
  public CommandGateway commandGateway(ApplicationContext applicationContext, CommandLogger commandLogger,
                                       SecurityManager securityManager, TxManager txManager,
                                       ValidationEngine validationEngine,
                                       ConfirmDocumentHandler confirmDocumentHandler) {
    CommandGateway commandGateway = new CommandGateway(commandLogger, securityManager, txManager, validationEngine);
    applicationContext.getBeansOfType(Handler.class).forEach((s, handler) -> {
      Class cmdClass = (Class) ((ParameterizedType) handler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
      commandGateway.registerHandler(cmdClass, handler);
    });
    commandGateway.registerHandler(ConfirmDocumentCommand.class, confirmDocumentHandler::confirm);
    commandGateway.registerHandler(ConfirmDocumentOnBehalfCommand.class, confirmDocumentHandler::confirmOnBehalf);
    return commandGateway;
  }

}
