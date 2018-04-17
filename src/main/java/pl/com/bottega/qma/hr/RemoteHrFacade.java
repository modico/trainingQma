package pl.com.bottega.qma.hr;

import java.util.Set;

public class RemoteHrFacade implements HrFacade {
  @Override
  public boolean belongsToAnyDepratment(Set<String> depratmentCodes, Long employeeId) {
    return true;
  }
}
