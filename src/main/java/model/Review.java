package model;

public class Review {
  private long id;
  private int rate;
  private String text;
  private long client_id;
  private long order_id;

  public Review(long id, int rate, String text, long client_id, long order_id) {
    this.id = id;
    this.rate = rate;
    this.text = text;
    this.client_id = client_id;
    this.order_id = order_id;
  }

  public Review(int rate, String text, long client_id, long order_id) {
    this.rate = rate;
    this.text = text;
    this.client_id = client_id;
    this.order_id = order_id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Integer getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getClientId() {
    return client_id;
  }

  public void setClient_id(long client_id) {
    this.client_id = client_id;
  }

  public Long getOrderId() {
    return order_id;
  }

  public void setOrder_id(long order_id) {
    this.order_id = order_id;
  }

  @Override
  public String toString() {
    return "Review{" +
        "id=" + id +
        ", rate=" + rate +
        ", text='" + text + '\'' +
        ", client_id=" + client_id +
        ", order_id=" + order_id +
        '}';
  }
}
