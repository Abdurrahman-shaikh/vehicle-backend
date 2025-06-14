package com.tcs.controller;

import com.tcs.entity.Underwriter;
import com.tcs.repository.UserRepository;
import com.tcs.service.UnderwriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UnderwriterController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UnderwriterService underwriterService;

    @GetMapping("/underwriters")
    public List<Underwriter> getAllUnderwriters(){
        return (List<Underwriter>) underwriterService.getAllUnderwriters();
    }

    @PostMapping("/underwriters")
    public Underwriter addUnderwriter(@RequestBody Underwriter underwriter){
        return underwriterService.register(underwriter);
    }

    @PostMapping("/login")
    public String varify(@RequestBody Underwriter underwriter){
        return underwriterService.varify(underwriter);
    }
}
