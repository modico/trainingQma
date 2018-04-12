package pl.com.bottega.qma.core.validation;

import java.lang.reflect.Field;

public class ValidationEngine {
  public ValidationErrors validate(Object object) {
    ValidationErrors errors = new ValidationErrors();
    for(Field field : object.getClass().getDeclaredFields()) {
      ValidatePresence validatePresence = field.getAnnotation(ValidatePresence.class);
      if(validatePresence != null)
        try {
          handlePresenceValidation(object, field, errors, validatePresence);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
    }
    return errors;
  }

  private void handlePresenceValidation(Object object, Field field, ValidationErrors errors, ValidatePresence validatePresence) throws IllegalAccessException {
    Object value = field.get(object);
    if(value == null)
      errors.addError(field.getName(), "can't be blank");
  }
}
