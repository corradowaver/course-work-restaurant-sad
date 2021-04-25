package model;

public class Client extends User{
  private Long id;
  private String name;
  private String address;
  private String phone;

  public Client(String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  public Client() {
  }

  public Client(Long id, String name, String address, String phone, User user) {
    super(user.getId(), user.getUsername(), user.getPassword());
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "Client{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", phone='" + phone + '\'' +
        '}';
  }
}
