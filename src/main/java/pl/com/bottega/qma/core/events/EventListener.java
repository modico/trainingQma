package pl.com.bottega.qma.core.events;

public interface EventListener<EventT> {
  void onEvent(EventT event);
}
