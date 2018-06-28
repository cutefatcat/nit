package com.nd.nit.controllers;

import com.nd.nit.dao.FileInfoDao;
import com.nd.nit.dao.VersionDao;
import com.nd.nit.models.CreateVersionModel;
import com.nd.nit.models.FileInfoModel;
import com.nd.nit.models.VersionModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/version")
public class VersionController extends BaseController {

    @RequestMapping("")
    public ResponseEntity getAll(){
        List<VersionModel> versionsList;

        try (Connection con = createConnection()){
            VersionDao versionDao = new VersionDao(con);
            versionsList = versionDao.getAll();
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

        return ResponseEntity.ok()
                .body(versionsList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    //Response для контроля ошибок на сервере
    public ResponseEntity get(@PathVariable("id") String id ){
        CreateVersionModel createVersionModel = new CreateVersionModel();
        Integer versionId;
        if (id.equals("last")){
            versionId = null;
        } else {
           try {
               versionId = Integer.parseInt(id);
           } catch (NumberFormatException e){
               return ResponseEntity
                       .status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body(e.getMessage());
           }
        }

        try (Connection con = createConnection()){
            VersionDao versionDao = new VersionDao(con);
            VersionModel versionModel = versionId != null ? versionDao.get(versionId) : versionDao.getLast();
            createVersionModel.setVersionModel(versionModel);
            FileInfoDao fileInfoDao= new FileInfoDao(con);
            createVersionModel.setInfoModelList(fileInfoDao.getByVersionId(versionModel.getId()));
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

        return ResponseEntity.ok()
                .body(createVersionModel);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody CreateVersionModel createVersionModel){//String //CreateVersionModel
        try (Connection con = createConnection()){
            VersionDao versionDao = new VersionDao(con);
            int versionId = versionDao.create(createVersionModel.getVersionModel());
            createVersionModel.getVersionModel().setId(versionId);

            FileInfoDao fileInfoDao = new FileInfoDao(con);

            for (int i = 0; i < createVersionModel.getInfoModelList().size(); i++) {
                FileInfoModel fileInfoModel = createVersionModel.getInfoModelList().get(i);
                fileInfoModel.setVersionId(versionId);
                int fileInfoId = fileInfoDao.create(fileInfoModel);
                fileInfoModel.setId(fileInfoId);
            }

        } catch (SQLException | ClassNotFoundException e) {
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(e.getMessage());
        }

        return ResponseEntity.ok()
                .body(createVersionModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<VersionModel> update(@PathVariable("id") int id, @RequestBody VersionModel versionModel){
        try (Connection con = createConnection()){
            VersionDao versionDao = new VersionDao(con);
            versionDao.update(id, versionModel);
            return ResponseEntity.ok()
            .body(versionDao.get(id));
        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
