package me.smash.payroll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
public class Manager {

  public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  @GeneratedValue
  @Id
  private Long id;
  private String name;
  @JsonIgnore
  private String password;
  private String[] roles;
  @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
  private Set<Employee> employees;



  public Manager() {
  }

  public Manager(String name, String password, String... roles) {
    this.name = name;
    setPassword(password);
    this.roles = roles;
  }

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(Set<Employee> employees) {
    this.employees = employees;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = PASSWORD_ENCODER.encode(password);
  }

  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
    this.roles = roles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Manager manager = (Manager) o;
    return Objects.equals(id, manager.id) &&
        Objects.equals(name, manager.name) &&
        Objects.equals(password, manager.password) &&
        Arrays.equals(roles, manager.roles);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(id, name, password);
    result = 31 * result + Arrays.hashCode(roles);
    return result;
  }

  @Override
  public String toString() {
    return "Manager{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", roles=" + Arrays.toString(roles) +
        '}';
  }
}
