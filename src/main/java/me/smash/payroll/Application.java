package me.smash.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class Application {

  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
  private ManagerRepository managerRepository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  @Bean
  CommandLineRunner databaseLoader() {
    return args -> {
      Manager greg = this.managerRepository.save(new Manager("greg", "turnquist",
          "ROLE_MANAGER"));
      Manager oliver = this.managerRepository.save(new Manager("oliver", "gierke",
          "ROLE_MANAGER"));

      SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken("greg", "doesn't matter",
              AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

      this.employeeRepository.save(new Employee("Frodo", "Baggins", "ring bearer", greg));
      this.employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar", greg));
      this.employeeRepository.save(new Employee("Gandalf", "the Grey", "wizard", greg));

      SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken("oliver", "doesn't matter",
              AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

      this.employeeRepository.save(new Employee("Samwise", "Gamgee", "gardener", oliver));
      this.employeeRepository.save(new Employee("Merry", "Brandybuck", "pony rider", oliver));
      this.employeeRepository.save(new Employee("Peregrin", "Took", "pipe smoker", oliver));

      SecurityContextHolder.clearContext();
    };
  }
}
