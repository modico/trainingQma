package pl.com.bottega.qma.core.events;

public class DefaultEventEngine implements EventEngine {
  @Override
  public <EventT> void addListener(Class<EventT> eventClass, EventListener<EventT> listener) {

  }

  @Override
  public void publish(Object event) {

  }
}
