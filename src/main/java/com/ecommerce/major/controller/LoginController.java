package com.ecommerce.major.controller;

import com.ecommerce.major.global.GlobalData;
import com.ecommerce.major.model.Role;
import com.ecommerce.major.model.User;
import com.ecommerce.major.repository.RoleRepository;
import com.ecommerce.major.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/login")
    public String getLogin(){
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("user")User user, HttpServletRequest httpServletRequest) throws ServletException {
        String password=user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles=new ArrayList<>();
        roles.add(roleRepository.findById(2).get());
        user.setRoles(roles);
        userRepository.save(user);
        httpServletRequest.login(user.getEmail(),password);
        return "redirect:/";
    }
}
