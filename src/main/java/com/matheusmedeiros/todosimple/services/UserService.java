package com.matheusmedeiros.todosimple.services;

import com.matheusmedeiros.todosimple.models.User;
import com.matheusmedeiros.todosimple.repositories.UserRepository;
import com.matheusmedeiros.todosimple.services.exceptions.DataBindingViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;




@Service // se comunica com com os repositorios
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id); //torna opcional o return da função

            // o RuntimeException é uma excessão caso o   Optional<User> não encontre um User pelo Id
            // forma mais simples de fazer sem precisar usar um if e else
        return user.orElseThrow(() -> new RuntimeException(
                "usuario não encontrado! id: " + id + ", tipo: " + User.class.getName()
        ));
    }

    @Transactional
        // usar sempre que for salvar algo no banco
        // Segue o padrão de banco de dados, ATOMICIDADE, ou cria ou não faz nada
    public User create(User obj){
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj){
        User newObj = findById(obj.getId()); // garante que o usuario que será atualizado existe
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(long id){
        findById(id);

        // verifica se id existe e se ele pode ser deletado
        //Se o user tiver entidades relacionados a ele  (tasks) ele não pode ser deletado
        try {
            this.userRepository.deleteById(id);
        }catch (Exception e){
            throw new DataBindingViolationException("Não é possivel deletar o User! pois este usuario tem tasks em aberto");
        }
    }


}

