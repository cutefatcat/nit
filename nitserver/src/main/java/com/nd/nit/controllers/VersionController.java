package com.nd.nit.controllers;

import com.nd.nit.models.Version;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/version")
public class VersionController {

    @RequestMapping("")
    public List<Version> getAll(){
        List<Version> versionList = new ArrayList<>();
        versionList.add(new Version(1, UUID.randomUUID().toString()));
        versionList.add(new Version(2, UUID.randomUUID().toString()));
        return versionList;
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public Version get(@PathVariable("token") String token ){
        Random r = new Random();
        return new Version(r.nextInt(), token);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String create(@RequestBody Version version){
        return "Created. Ra-pa-pa-pa!";
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.PUT)
    public Version update(@PathVariable("token") String token, @RequestBody Version version){
        return version;
    }
}
