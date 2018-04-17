package pl.com.bottega.qma.notifications;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.bottega.qma.core.events.EventSubscriber;

@Configuration
public class NotificationsConfig {

  @Bean
  public DocumentPublishedListener documentPublishedListener(EventSubscriber eventSubscriber) {
    return new DocumentPublishedListener(eventSubscriber);
  }

}
