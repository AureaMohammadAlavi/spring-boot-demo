package me.smash.payroll;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ManagerRepository extends CrudRepository<Manager, Long> {
  Manager findByName(String name);
}
