// Repository interface for data access

package com.nag.taskmanager.repository;

import com.nag.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTaskByName(String name);
    List<Task> findTaskByCompleted(boolean completed);

}
