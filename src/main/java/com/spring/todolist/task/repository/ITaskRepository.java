package com.spring.todolist.task.repository;

import com.spring.todolist.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByIdUsuario(UUID idUsuario);
    Task findByIdAndIdUsuario(UUID id, UUID idUsuario);
}
