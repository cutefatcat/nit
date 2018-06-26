package com.nd.nit.controllers;


import com.nd.nit.dao.FileBinaryDao;
import com.nd.nit.dao.FileInfoDao;
import com.nd.nit.models.FileBinaryModel;
import com.nd.nit.util.HashUtil;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {
    @RequestMapping(value = "/{file_id}", method = RequestMethod.GET)
    public ResponseEntity<Resource> get(@PathVariable("file_id") int id ) throws IOException {
        FileBinaryModel fileBinaryModel;

        try (Connection con = createConnection()) {
            FileBinaryDao fileBinaryDao = new FileBinaryDao(con);
            fileBinaryModel = fileBinaryDao.get(id);

        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileBinaryModel.getContent()));

        return ResponseEntity.ok()
                .headers(null)
                .contentLength(fileBinaryModel.getContent().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @RequestMapping(value = "/{file_id}", method = RequestMethod.POST)
    public ResponseEntity<String> create(@PathVariable("file_id") int id, @RequestParam("file") MultipartFile file){
        try (Connection con = createConnection()){
            FileBinaryDao fileBinaryDao = new FileBinaryDao(con);
            FileBinaryModel fileBinaryModel = new FileBinaryModel();
            fileBinaryModel.setContent(file.getBytes());
            fileBinaryModel.setHashContent(HashUtil.calculateHash(file));

            int fileBinaryId = fileBinaryDao.create(fileBinaryModel);
            FileInfoDao fileInfoDao = new FileInfoDao(con);
            fileInfoDao.updateFileBinaryId(id, fileBinaryId);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok()
                .body("Created");
    }
}
