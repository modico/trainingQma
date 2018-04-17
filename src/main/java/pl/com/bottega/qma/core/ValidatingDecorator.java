package pl.com.bottega.qma.core;

import pl.com.bottega.qma.core.validation.ValidationEngine;

public class ValidatingDecorator<CommandT extends Command, ReturnT> extends HandlerDecorator<CommandT, ReturnT> {
  private final ValidationEngine validationEngine;

  protected ValidatingDecorator(Handler<CommandT, ReturnT> decoratedHandler, ValidationEngine validationEngine) {
    super(decoratedHandler);
    this.validationEngine = validationEngine;
  }

  @Override
  public ReturnT handle(CommandT command) {
    var errors = validationEngine.validate(command);
    if(errors.isInvalid()) {
      throw new IllegalArgumentException();
    }
    return decoratedHandler.handle(command);
  }
}
