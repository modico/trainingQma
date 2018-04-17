package pl.com.bottega.qma.core.events;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultEventEngine implements EventSubscriber, EventPublisher {

  private final Map<Class, Set<EventListener>> listeners = new HashMap<>();

  @Override
  public <EventT> void subscribe(Class<EventT> eventClass, EventListener<EventT> listener) {
    if (listeners.containsKey(eventClass)) {
      listeners.get(eventClass).add(listener);
    } else {
      listeners.put(eventClass, Sets.newHashSet(listener));
    }
  }

  @Override
  public void publish(Object event) {
    if (listeners.containsKey(event.getClass())) {
      listeners.get(event.getClass()).forEach(eventListener -> eventListener.onEvent(event));
    }
  }
}
