package pl.com.bottega.qma.core;

class TransactionalHandler<CommandT extends Command, ReturnT> extends HandlerDecorator<CommandT, ReturnT> {

  private final TxHandler<CommandT, ReturnT> txHandler;
  private final TxManager txManager;

  TransactionalHandler(TxHandler<CommandT, ReturnT> txHandler, Handler<CommandT, ReturnT> decoratedHandler, TxManager txManager) {
    super(decoratedHandler);
    this.txHandler = txHandler;
    this.txManager = txManager;
  }

  @Override
  public ReturnT handle(CommandT command) {
    txHandler.preTx(command);
    ReturnT retVal = null;
    try {
      txManager.begin();
      retVal = decoratedHandler.handle(command);
      txManager.commit();
    }
    catch (RuntimeException ex) {
      txManager.rollback();
      throw ex;
    }
    txHandler.postTx(command);
    return retVal;
  }
}
