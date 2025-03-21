// Repository interface for data access

package com.nag.taskmanager.repository;

import com.nag.taskmanager.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByLastName(String lastName);
}
