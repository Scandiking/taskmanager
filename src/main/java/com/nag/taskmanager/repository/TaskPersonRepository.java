package com.nag.taskmanager.repository;

import com.nag.taskmanager.model.TaskPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskPersonRepository extends JpaRepository<TaskPerson, Long> {
    List<TaskPerson> findByTask_Id(Long taskId);
    List <TaskPerson> findByPerson_Id(Long personId);
    Optional<TaskPerson> findByPerson_IdAndTask_Id(Long personId, Long taskId);
}
