package com.nag.taskmanager.repository;

import com.nag.taskmanager.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // JpaRepository provides built-in methods for CRUD operations:
    // save(), findOne(), findById(), findAll(), count(), delete(), deleteById(), existsById(), ...
    // You can add custom query methods if needed, for example:
    // List<Room> findByCapacityGreaterThanEqual(int capacity);
}
