package model;

public class Staff extends User {
  private Long id;
  private String firstName;
  private String lastName;
  private int salary;
  private long departmentId;
  private String role;

  public Staff(Long id, String firstName, String lastName, int salary, long departmentId, String role) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
    this.departmentId = departmentId;
    this.role = role;
  }

  public Staff(String firstName, String lastName, int salary, long departmentId, String role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
    this.departmentId = departmentId;
    this.role = role;
  }

  public Staff(String firstName, String lastName, int salary, long departmentId, String role, User user) {
    super(user.getId(), user.getUsername(), user.getPassword());
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
    this.departmentId = departmentId;
    this.role = role;
  }

  public Staff(long id, String firstName, String lastName, int salary, long departmentId, String role, User user) {
    super(user.getId(), user.getUsername(), user.getPassword());
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
    this.departmentId = departmentId;
    this.role = role;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(long departmentId) {
    this.departmentId = departmentId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public long getUserId() {return super.getId();}


  @Override
  public String toString() {
    return "Staff{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", salary=" + salary +
        ", departmentId=" + departmentId +
        ", role='" + role + '\'' +
        '}';
  }
}
