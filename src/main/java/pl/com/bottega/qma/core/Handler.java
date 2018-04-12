package pl.com.bottega.qma.core;

public interface Handler<CommandT extends Command, ReturnT> {

  ReturnT handle(CommandT commandT);

}
