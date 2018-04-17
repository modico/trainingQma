package pl.com.bottega.qma.notifications;

import pl.com.bottega.qma.core.events.EventEngine;
import pl.com.bottega.qma.core.events.EventEngine.EventListener;
import pl.com.bottega.qma.docflow.events.DocumentPublished;

public class DocumentPublishedListener implements EventListener<DocumentPublished> {

  public DocumentPublishedListener(EventEngine eventEngine) {
    eventEngine.addListener(DocumentPublished.class, this);
  }

  @Override
  public void onEvent(DocumentPublished event) {
    System.out.println("Notifying people about event published");
  }
}
