package model;


import java.util.Objects;

public class Employee {

  private final String name;
  private final String surname;
  private int department;
  private int salary;

  public Employee(String name,
      String surname,
      int department,
      int salary) {
    this.name = name;
    this.surname = surname;
    this.department = department;
    this.salary = salary;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public int getDepartment() {
    return department;
  }

  public void setDepartment(int department) {
    this.department = department;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return Objects.equals(name, employee.name) && Objects.equals(surname, employee.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname);
  }

  @Override
  public String toString() {
    return String.format("ФИО: %s %s, \n Отдел: %d, \n ЗП: %d", surname, name, department, salary);
  }

}
