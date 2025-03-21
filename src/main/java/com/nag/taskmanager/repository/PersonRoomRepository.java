// Repository interface for data access

package com.nag.taskmanager.repository;

import com.nag.taskmanager.model.PersonRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRoomRepository extends JpaRepository<PersonRoom, Long> {
    List<PersonRoom> findByPerson_Id(Long personId);
    List<PersonRoom> findByRoom_Id(Long roomId);
    Optional<PersonRoom> findByPerson_IdAndRoom_Id(Long personId, Long roomId);

}
