package pl.com.bottega.qma.core.validation;

import java.lang.reflect.Field;
import java.math.BigDecimal;

class BigDecimalNumberValidator extends FieldValidator<BigDecimal> {

  private final ValidateNumber config;

  public BigDecimalNumberValidator(Field field, ValidateNumber config) {
    super(field);
    this.config = config;
  }

  @Override
  protected void validateValue(BigDecimal value, ValidationErrors errors) {
    if (value == null) {
      return;
    }
    int compMin = value.compareTo(new BigDecimal(config.min()));
    int compMax = value.compareTo(new BigDecimal(config.max()));
    if (compMin < 0 || compMax > 0) {
      addError(errors, String.format("must be between %d and %d", config.min(), config.max()));
    }
  }


}
