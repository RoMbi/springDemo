package com.mySpring.demo.repository;

import com.mySpring.demo.entity.user.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface UserRoleRepository extends CrudRepository<Role, Long> {
}
