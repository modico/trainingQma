package pl.com.bottega.qma.core.events;

public interface EventSubscriber {

  <EventT> void subscribe(Class<EventT> eventClass, EventListener<EventT> listener);

}
