package pl.com.bottega.qma.core.validation;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

public class ValidateSizeTest {

  private class Bean {

    @ValidatePresence
    @ValidateSize(min = 3, max = 5)
    String field = "ala";

    @ValidateSize(max = 2)
    Collection<String> strings = new LinkedList<>();

  }

  private class InvalidUsageBean {

    @ValidateSize
    Integer size;

  }

  private ValidationEngine engine = new ValidationEngine();

  private Bean bean = new Bean();

  @Test
  public void validatesPositivelyIfSizesInRange() {
    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isValid()).isTrue();
  }

  @Test
  public void validatesPresence() {
    bean.field = null;

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isInvalid()).isTrue();
    assertThat(errors.getErrors("field")).contains("can't be blank");
  }

  @Test
  public void validatesStringToShort() {
    bean.field = "a";

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isInvalid()).isTrue();
    assertThat(errors.getErrors("field")).contains("must have size between 3 and 5");
  }

  @Test
  public void validatesStringToLong() {
    bean.field = "aaaaaa";

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isInvalid()).isTrue();
    assertThat(errors.getErrors("field")).contains("must have size between 3 and 5");
  }

  @Test
  public void validatesCollectionToLong() {
    bean.strings.add("1");
    bean.strings.add("2");
    bean.strings.add("3");

    ValidationErrors errors = engine.validate(bean);

    assertThat(errors.isInvalid()).isTrue();
    assertThat(errors.getErrors("strings")).contains("must have size between 0 and 2");
  }

  @Test
  public void cantValidateSizeOnTypesWoLengthAndSize() {
    assertThatThrownBy(() -> engine.validate(new InvalidUsageBean())).isInstanceOf(IllegalArgumentException.class);
  }

}
