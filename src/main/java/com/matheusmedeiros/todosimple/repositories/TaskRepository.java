package com.matheusmedeiros.todosimple.repositories;

import com.matheusmedeiros.todosimple.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // FORMA com Spring puro
    // o "_" se refere a um atributo dentro da table
        List<Task> findByUser_Id(Long id);

    // FORMA Query de fazer
    //@Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
    //List<Task> findByUserId(@Param("id") Long id);

    //FORMA com SQL puro
    //@Query(value = "SELECT * FROM task t WHERE t.user.user_id = :id", nativeQuery = true)
    //List<Task> findByUserId(@Param("id") Long id);
}
