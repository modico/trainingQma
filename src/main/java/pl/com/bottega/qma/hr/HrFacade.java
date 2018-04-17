package pl.com.bottega.qma.hr;

import java.util.Set;

public interface HrFacade {

  boolean belongsToAnyDepratment(Set<String> depratmentCodes, Long employeeId);

}
