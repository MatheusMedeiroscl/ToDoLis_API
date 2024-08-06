package com.matheusmedeiros.todosimple.controllers;


import com.matheusmedeiros.todosimple.models.User;
import com.matheusmedeiros.todosimple.services.UserService;
import com.matheusmedeiros.todosimple.models.User.CreateUser;
import com.matheusmedeiros.todosimple.models.User.UpdateUser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    // localhost:8080/user/1
    @GetMapping("/{id}") // conceito HTTP de Get para levar o user a tela do usuario desejado
        // @PathVariable correlaciona o id do GetMapping com o id da função
    public ResponseEntity<User> findById( @PathVariable Long id){
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);

    }

    @PostMapping
    @Validated(CreateUser.class)
    public ResponseEntity<Void> create(@Valid @RequestBody User obj){
        this.userService.create(obj);
        //Builder que pega contexto do user para encontrar e gerar o user
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(obj.getId()).toUri();

        return  ResponseEntity.created(uri).build();


    }

    @PutMapping("/{id}")
    @Validated(UpdateUser.class)
    public  ResponseEntity<Void> uptdate(@Valid @RequestBody User obj, @PathVariable Long id){
        obj.setId(id);
        this.userService.update(obj);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
