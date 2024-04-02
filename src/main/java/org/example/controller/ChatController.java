package org.example.controller;

import org.example.nettychart.SpringNettyClient;
import org.example.pojo.Person;
import org.example.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/chat")
public class ChatController {


    @Autowired
    private SpringNettyClient client;
    @PostMapping("/msg")
    public void sendMessage( Person person) throws Exception {
       client.start(person.getMsg());
    }

    @GetMapping("t")
    public String test(){
        return "123";
    }
}
