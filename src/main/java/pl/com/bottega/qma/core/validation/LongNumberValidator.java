package pl.com.bottega.qma.core.validation;

import java.lang.reflect.Field;

class LongNumberValidator extends FieldValidator<Long> {

  private final ValidateNumber config;

  public LongNumberValidator(Field field, ValidateNumber config) {
    super(field);
    this.config = config;
  }

  @Override
  protected void validateValue(Long value, ValidationErrors errors) {
    if(value == null) {
      return;
    }
    if(value > config.max() || value < config.min()) {
      addError(errors, String.format("must be between %d and %d", config.min(), config.max()));
    }
  }


}
