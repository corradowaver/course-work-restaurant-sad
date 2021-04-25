package model;

public class Position {
  private long id;
  private String name;
  private int price;
  private int realPrice;

  public Position(long id, String name, int price, int realPrice) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.realPrice = realPrice;
  }

  public Position(String name, int price, int realPrice) {
    this.name = name;
    this.price = price;
    this.realPrice = realPrice;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Integer getRealPrice() {
    return realPrice;
  }

  public void setRealPrice(int realPrice) {
    this.realPrice = realPrice;
  }

  @Override
  public String toString() {
    return "Position{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        ", realPrice=" + realPrice +
        '}';
  }
}
