package pl.com.bottega.qma.core.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

public class ValidateRegexpTest {

  private class Bean {

    @ValidateRegexp(expression = "^a.*$")
    @ValidateSize(max = 3)
    String field = "ala";

  }

  private class InvalidUsageBean {

    @ValidateRegexp(expression = "x")
    Double field;

  }

  private ValidationEngine engine = new ValidationEngine();

  private Bean bean = new Bean();

  @Test
  public void validatesPositivelyIfRegexpMatches() {
    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isValid()).isTrue();
  }

  @Test
  public void validatesPositivelyIfStringNull() {
    bean.field = null;

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isValid()).isTrue();
  }

  @Test
  public void validatesIfStringMatchesRegexp() {
    bean.field = "bla";

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isValid()).isFalse();
    assertThat(errors.getErrors("field")).contains("has wrong format");
  }

  @Test
  public void chainsValidators() {
    bean.field = "blahh";

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isValid()).isFalse();
    assertThat(errors.getErrors("field")).contains("has wrong format", "must have size between 0 and 3");
  }

  @Test
  public void cantValidateRegexpOnNonStringTypes() {
    assertThatThrownBy(() -> engine.validate(new InvalidUsageBean())).isInstanceOf(IllegalArgumentException.class);
  }

}
