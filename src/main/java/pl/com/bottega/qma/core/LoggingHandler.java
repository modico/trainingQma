package pl.com.bottega.qma.core;

public class LoggingHandler<CommandT extends Command, ReturnT> extends HandlerDecorator<CommandT, ReturnT> {
  private final CommandLogger commandLogger;

  LoggingHandler(Handler<CommandT, ReturnT> decoratedHandler, CommandLogger commandLogger) {
    super(decoratedHandler);
    this.commandLogger = commandLogger;
  }

  @Override
  public ReturnT handle(CommandT command) {
    commandLogger.executionStarted(command);
    var retVal = decoratedHandler.handle(command);
    commandLogger.executionFinished(command);
    return retVal;
  }
}
