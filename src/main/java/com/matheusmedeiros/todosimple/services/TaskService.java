package com.matheusmedeiros.todosimple.services;

import com.matheusmedeiros.todosimple.models.Task;
import com.matheusmedeiros.todosimple.models.User;
import com.matheusmedeiros.todosimple.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service // se comunica com com os repositorios
public class TaskService {


    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userServices;



    public Task findById(long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
                "Task não encontrada! id: " + id + ", tipo: " + Task.class.getName()
        ));
    }

    @Transactional
    public  Task createTask(Task obj){
        //primeiro pesquisar se o user da task existe
        User user = this.userServices.findById(obj.getUser().getId());
        obj.setId();
        obj.setUser(user);
        obj = this.taskRepository.save(obj);

        return obj;
    }

    @Transactional
    public Task uptdateTask(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());

        return this.taskRepository.save(newObj);
    }

    public void deleteTask(long id){
        findById(id);

        // verifica se id existe e se ele pode ser deletado
        //Se o user tiver entidades relacionados a ele  (tasks) ele não pode ser deletado
        try {
            this.taskRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Não é possivel deletar esta task! pois a entidades relacionadas");
        }
    }
}
