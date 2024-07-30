package com.matheusmedeiros.todosimple.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = Task.TABLE_NAME)
public class Task {
    public static final String TABLE_NAME = "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false) //faz referencia a chave primaria da tabela User
    private User user;

    @Column(name = "description", length = 255, nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    public Task() {}

    public Task(long id, User user, String description) {
        this.id = id;
        this.user = user;
        this.description = description;
    }

    public long getId() {return id;}
    public void setId() {this.id = id;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public @NotBlank @Size(min = 1, max = 255) String getDescription() {return description;}
    public void setDescription(@NotBlank @Size(min = 1, max = 255) String description) {this.description = description;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(user, task.user) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
