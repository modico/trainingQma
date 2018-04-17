package pl.com.bottega.qma.hr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HrConfig {

  @Bean
  public HrFacade hrFacade() {
    return new RemoteHrFacade();
  }

}
