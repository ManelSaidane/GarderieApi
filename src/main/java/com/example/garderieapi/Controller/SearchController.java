package com.example.garderieapi.Controller;
import com.example.garderieapi.Service.UserService;
import com.example.garderieapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private UserService userService;

    @GetMapping("/nom/{nom}")
    public List<User> searchByNom(@PathVariable String nom){
        return userService.findByNom(nom);
    }

    @GetMapping("/role/{role}")
    public List<User> searchByRole(@PathVariable String role){

        return userService.getUserByRolesName(role);
    }

}
