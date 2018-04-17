package pl.com.bottega.qma.core.events;

public class DefaultEventEngine implements EventSubscriber, EventPublisher {
  @Override
  public <EventT> void subscribe(Class<EventT> eventClass, EventListener<EventT> listener) {

  }

  @Override
  public void publish(Object event) {

  }
}
