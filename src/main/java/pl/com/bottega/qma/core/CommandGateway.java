package pl.com.bottega.qma.core;

import java.util.HashMap;
import java.util.Map;

public class CommandGateway {

  public Map<Class<? extends Command>, Handler> handlersMap = new HashMap<>();

  private CommandLogger commandLogger;
  private SecurityManager securityManager;
  private TxManager txManager;

  public CommandGateway(CommandLogger commandLogger, SecurityManager securityManager,
                        TxManager txManager) {
    this.commandLogger = commandLogger;
    this.securityManager = securityManager;
    this.txManager = txManager;
  }

  public <ReturnT> ReturnT execute(Command command) {
    Handler<Command, ReturnT> handler = handlerFor(command);
    return handler.handle(command);
  }

  public <CommandT extends Command> void registerHandler(Class<CommandT> commandClass, Handler<CommandT, ?> handler) {
    Handler<CommandT, ?> decoratedHandler = new ProfilingHandler<>(handler);
    decoratedHandler = new LoggingHandler<>(decoratedHandler, commandLogger);
    if(handler instanceof TxHandler) {
      decoratedHandler = new TransactionalHandler<>((TxHandler) handler, decoratedHandler, txManager);
    }
    decoratedHandler = new SecureHandler(decoratedHandler, handler, securityManager);
    handlersMap.put(commandClass, decoratedHandler);
  }

  private Handler handlerFor(Command command) {
    if (!handlersMap.containsKey(command.getClass())) {
      throw new IllegalArgumentException(String.format("No handler found for %s", command.getClass()));
    }
    return handlersMap.get(command.getClass());
  }

}
