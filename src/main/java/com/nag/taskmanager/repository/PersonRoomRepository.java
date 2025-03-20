package com.nag.taskmanager.repository;

import com.nag.taskmanager.model.PersonRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRoomRepository extends JpaRepository<PersonRoom, Long> {
    List<PersonRoom> findByPersonId(Long personId);
    List<PersonRoom> findByRoomId(Long roomId);
    Optional<PersonRoom> findByPersonIdAndRoomId(Long personId, Long roomId);

}
