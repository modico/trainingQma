package pl.com.bottega.qma.core;

public interface TxHandler<CommandT extends Command, ReturnT> extends Handler<CommandT, ReturnT> {

  void preTx(CommandT command);

  void postTx(CommandT command);

}
