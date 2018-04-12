package pl.com.bottega.qma.core;

abstract class HandlerDecorator<CommandT extends Command, ReturnT> implements Handler<CommandT, ReturnT> {

  protected final Handler<CommandT, ReturnT> decoratedHandler;

  protected HandlerDecorator(Handler<CommandT, ReturnT> decoratedHandler) {
    this.decoratedHandler = decoratedHandler;
  }

}
