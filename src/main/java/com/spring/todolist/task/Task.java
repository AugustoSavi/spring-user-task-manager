package com.spring.todolist.task;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID idUsuario;
    private String description;
    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Version
    private Long version;


    public void setTitle(String title) throws Exception {
        if (title.length() > 50){
            throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }

        this.title = title;
    }
}
