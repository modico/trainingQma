package pl.com.bottega.qma.core.validation;

import java.lang.reflect.Field;

class PresenceValidator extends FieldValidator {

  private final ValidatePresence config;

  public PresenceValidator(Field field, ValidatePresence config) {
    super(field);
    this.config = config;
  }

  @Override
  protected void validateValue(Object value, ValidationErrors errors) {
    if (value == null) {
      addError(errors);
      return;
    }
    if (!config.allowEmpty()) {
      fieldSize(value).ifPresent((size) -> {
        if (size.equals(0)) {
          addError(errors);
        }
      });
    }
  }

  private void addError(ValidationErrors errors) {
    addError(errors, "can't be blank");
  }
}
