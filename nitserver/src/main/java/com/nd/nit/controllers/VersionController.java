package com.nd.nit.controllers;

import com.nd.nit.dao.VersionDao;
import com.nd.nit.models.Version;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/version")
public class VersionController {

    @RequestMapping("")
    public ResponseEntity<List<Version>> getAll(){
        List<Version> versionsList;

        try (Connection con = createConnection()){
            VersionDao versionDao = new VersionDao(con);
            versionsList = versionDao.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        return ResponseEntity.ok()
                .body(versionsList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    //Response для контроля ошибок на сервере
    public ResponseEntity<Version> get(@PathVariable("id") int id ){
        Version version;
        try (Connection con = createConnection()){
            VersionDao versionDao = new VersionDao(con);
            version = versionDao.get(id);
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        return ResponseEntity.ok()
                .body(version);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody Version version){
        try (Connection con = createConnection()){
            VersionDao versionDao = new VersionDao(con);
            versionDao.create(version);
        } catch (SQLException | ClassNotFoundException e) {
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(e.getMessage());
        }
        return ResponseEntity.ok()
                .body("Created");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Version> update(@PathVariable("id") int id, @RequestBody Version version){
        try (Connection con = createConnection()){
            VersionDao versionDao = new VersionDao(con);
            versionDao.update(id, version);
            return ResponseEntity.ok()
            .body(versionDao.get(id));
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nit?useSSL=false", "root", "123qwe");
        return con;
    }
}
