package pl.com.bottega.qma.core;

class SecureHandler<CommandT extends Command, ReturnT> extends HandlerDecorator<CommandT, ReturnT> {

  private final Handler<CommandT, ReturnT> baseHandler;
  private final SecurityManager securityManager;

  protected SecureHandler(Handler<CommandT, ReturnT> decoratedHandler, Handler<CommandT, ReturnT> baseHandler, SecurityManager securityManager) {
    super(decoratedHandler);
    this.baseHandler = baseHandler;
    this.securityManager = securityManager;
  }

  @Override
  public ReturnT handle(CommandT command) {
    securityManager.checkSecurity(command, baseHandler);
    return decoratedHandler.handle(command);
  }
}
