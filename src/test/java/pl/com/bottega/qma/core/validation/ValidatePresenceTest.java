package pl.com.bottega.qma.core.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ValidatePresenceTest {

  private class Bean {

    @ValidatePresence
    String field;

    @ValidatePresence(allowEmpty = true)
    String otherField = "value";

  }

  private ValidationEngine engine = new ValidationEngine();

  @Test
  public void doesNotAllowEmptyStringsByDefault() {
    Bean bean = new Bean();
    bean.field = "";

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isInvalid()).isTrue();
  }

  @Test
  public void canAllowEmptyStrings() {
    Bean bean = new Bean();
    bean.field = "value";
    bean.otherField = "   ";

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isInvalid()).isFalse();
  }


}
