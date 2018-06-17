package com.nd.nit.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @RequestMapping("/version")
    public String getVersion(){
        return "Ra-pa-pa-pa!";
    }
}
