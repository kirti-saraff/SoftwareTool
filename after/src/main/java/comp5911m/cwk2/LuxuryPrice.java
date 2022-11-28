package comp5911m.cwk2;

public class LuxuryPrice extends Price {
  @Override
  public int getPriceCode() {
    return Car.LUXURY;
  }

  @Override
  public int getCharge(int daysRented) {
    return 50 * daysRented;
  }
}
