package com.spring.todolist.task.Controller;

import com.spring.todolist.task.Task;
import com.spring.todolist.task.repository.ITaskRepository;
import com.spring.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody Task task, HttpServletRequest request) {
        Object idUsuario = request.getAttribute("idUsuario");
        task.setIdUsuario((UUID) idUsuario);

        if (task.getStartAt().isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio não pode ser maior que a data de termino");
        }

        return ResponseEntity.ok(taskRepository.save(task));
    }

    @GetMapping
    public ResponseEntity read(HttpServletRequest request) {
        Object idUsuario = request.getAttribute("idUsuario");
        List<Task> all = taskRepository.findByIdUsuario((UUID) idUsuario);
        if (all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nenhum registro encontrado");
        }
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable UUID id, HttpServletRequest request) {
        Object idUsuario = request.getAttribute("idUsuario");
        Task task = taskRepository.findByIdAndIdUsuario(id, (UUID) idUsuario);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("nenhum registro encontrado");
        }
        return ResponseEntity.ok().body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody Task task, HttpServletRequest request) {
        Object idUsuario = request.getAttribute("idUsuario");

        Optional<Task> taskSearched = taskRepository.findById(id);
        if (taskSearched.isEmpty() || !taskSearched.get().getIdUsuario().equals(idUsuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("registro não encontrado");
        }
        Utils.CopyNoNullProperties(task, taskSearched.get());
        return ResponseEntity.ok().body(taskRepository.save(taskSearched.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id, HttpServletRequest request) {
        Object idUsuario = request.getAttribute("idUsuario");

        Optional<Task> taskSearched = taskRepository.findById(id);
        if (taskSearched.isEmpty() || !taskSearched.get().getIdUsuario().equals(idUsuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("registro não encontrado");
        }
        taskRepository.delete(taskSearched.get());

        return ResponseEntity.ok().body("registro deletado");
    }
}
