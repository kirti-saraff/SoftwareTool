package comp5911m.cwk2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LuxuryPriceTest {
  private Price price;

  @BeforeEach
  void setUp() {
    price = new LuxuryPrice();
  }

  @Test
  void priceCode() {
    assertThat(price.getPriceCode(), is(Car.LUXURY));
  }

  @Test
  void chargeOneDay() {
    assertThat(price.getCharge(1), is(50));
  }

  @Test
  void chargeTwoDays() {
    assertThat(price.getCharge(2), is(100));
  }

  @Test
  void renterPointsOneDay() {
    assertThat(price.getFrequentRenterPoints(1), is(1));
  }

  @Test
  void renterPointsThreeDays() {
    assertThat(price.getFrequentRenterPoints(3), is(1));
  }
}
