package pl.com.bottega.qma.core;

class ProfilingHandler<CommandT extends Command, ReturnT> extends HandlerDecorator<CommandT, ReturnT> {

  protected ProfilingHandler(Handler<CommandT, ReturnT> decoratedHandler) {
    super(decoratedHandler);
  }

  @Override
  public ReturnT handle(CommandT command) {
    var profiler = new Profiler(command, this);
    var retVal = decoratedHandler.handle(command);
    profiler.finished();
    return retVal;
  }
}
