package com.nd.nit.controllers;


import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/file")
public class FileController {
    @RequestMapping(value = "/{file_token}", method = RequestMethod.GET)
    public ResponseEntity<Resource> get(@PathVariable("file_token") String token ) throws IOException {
        File file = new File("test.txt");
        Files.write(file.toPath(), "j.".getBytes());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(null)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @RequestMapping(value = "/{file_token}", method = RequestMethod.POST)
    public String create(@PathVariable("file_token") String token, @RequestParam("file") MultipartFile file){
        return "Created. Ra-fa-fa-file!";
    }
}
