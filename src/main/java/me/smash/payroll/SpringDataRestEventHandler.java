package me.smash.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class SpringDataRestEventHandler {

  private final ManagerRepository managerRepository;
  private final EmployeeRepository employeeRepository;

  @Autowired
  public SpringDataRestEventHandler(ManagerRepository managerRepository,
      EmployeeRepository employeeRepository) {
    this.managerRepository = managerRepository;
    this.employeeRepository = employeeRepository;
  }

  @HandleBeforeCreate
  @HandleBeforeSave
  public void applyUserInformationUsingSecurityContext(Employee employee) {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    Manager manager = managerRepository.findByName(name);
    if (manager == null) {
      throw new IllegalStateException("Manager with name '" + name + "' not found");
    }
    if (employee.getId() != null) {
      manager.getEmployees().stream().filter(e-> e.getId().equals(employee.getId())).findFirst().orElseThrow(()->new AccessDeniedException("A manager is not allowed to modify other managers' employees"));
    }
    employee.setManager(manager);
  }
}
