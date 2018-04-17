package pl.com.bottega.qma.notifications;

import pl.com.bottega.qma.core.events.EventSubscriber;
import pl.com.bottega.qma.core.events.EventSubscriber.EventListener;
import pl.com.bottega.qma.docflow.events.DocumentPublished;

public class DocumentPublishedListener implements EventListener<DocumentPublished> {

  public DocumentPublishedListener(EventSubscriber eventEngine) {
    eventEngine.subscribe(DocumentPublished.class, this);
  }

  @Override
  public void onEvent(DocumentPublished event) {
    System.out.println("Notifying people about event published");
  }
}
