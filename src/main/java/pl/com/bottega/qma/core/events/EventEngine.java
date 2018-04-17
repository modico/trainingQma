package pl.com.bottega.qma.core.events;

public interface EventEngine extends EventPublisher {

  <EventT> void addListener(Class<EventT> eventClass, EventListener<EventT> listener);

  interface EventListener<EventT> {
    void onEvent(EventT event);
  }

}
