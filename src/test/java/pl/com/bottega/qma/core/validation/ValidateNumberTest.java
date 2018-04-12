package pl.com.bottega.qma.core.validation;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

public class ValidateNumberTest {

  private class Bean {

    @ValidateNumber(min = 10, max = 100)
    int n1 = 100;

    @ValidatePresence
    @ValidateNumber(min = 0, max = 15)
    Long n2 = 15L;

    @ValidatePresence
    @ValidateNumber(min = 10, max = 100)
    BigDecimal n3 = new BigDecimal(20.0);

  }

  private class InvalidUsageBean {

    @ValidateNumber(min = 10, max = 100)
    String abc = "ala";

  }

  private ValidationEngine engine = new ValidationEngine();

  private Bean bean = new Bean();

  @Test
  public void validatesPositivelyIfAllNumbersInRange() {
    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isValid()).isTrue();
  }

  @Test
  public void validatesPrimitiveFields() {
    bean.n1 = -1;

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isInvalid()).isTrue();
    assertThat(errors.getErrors("n1")).containsExactly("must be between 10 and 100");
  }

  @Test
  public void validatesNonPrimitiveFields() {
    bean.n2 = -1L;

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isInvalid()).isTrue();
    assertThat(errors.getErrors("n2")).containsExactly("must be between 0 and 15");
  }

  @Test
  public void validatesBigDecimalFields() {
    bean.n3 = new BigDecimal(-10);

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isInvalid()).isTrue();
    assertThat(errors.getErrors("n3")).containsExactly("must be between 10 and 100");
  }

  @Test
  public void cantValidateOnNonNumericTypes() {
    assertThatThrownBy(() -> engine.validate(new InvalidUsageBean())).isInstanceOf(IllegalArgumentException.class);
  }

}
