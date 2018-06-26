package com.nd.nit.dao;

import com.nd.nit.models.FileInfoModel;

import java.sql.*;

public class FileInfoDao {
    private final Connection con;
    private static final String FILE_TABLE_NAME = "file_info";

    public FileInfoDao(Connection con) {
        this.con = con;
    }

    public int create(FileInfoModel file) throws SQLException {
        try (PreparedStatement stmnt = con.prepareStatement(
                "INSERT INTO " + FILE_TABLE_NAME +
                        " (name, path, hash_fullname, version_id) VALUES (?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS)) {
            stmnt.setString(1, file.getName());
            stmnt.setString(2, file.getPath());
            stmnt.setString(3, file.getHashFullname());
            stmnt.setInt(4, file.getVersionId());
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            if (rs.next()) {
                int lastId = rs.getInt(1);
                return lastId;
            }
        }
        return 0;
    }

    public FileInfoModel get(int id) {
        try (PreparedStatement stmnt = con.prepareStatement(
                "SELECT * FROM " + FILE_TABLE_NAME + " WHERE id=?")) {
            stmnt.setInt(1, id);
            ResultSet resultSet = stmnt.executeQuery();
            resultSet.next();
            FileInfoModel file = new FileInfoModel();
            file.setId(resultSet.getInt("id"));
            file.setName(resultSet.getString("name"));
            file.setPath(resultSet.getString("path"));
            file.setVersionId(resultSet.getInt("version_id"));
            file.setBinaryId(resultSet.getInt("binary_id"));

            return file;
        } catch (SQLException e) {
            return null;
        }
    }

    public void updateFileBinaryId(int fileInfoId, int fileBinaryId){
        try (PreparedStatement stmnt = con.prepareStatement(
                "UPDATE " + FILE_TABLE_NAME + " SET binary_id=? WHERE id=?")) {
            stmnt.setInt(1, fileBinaryId);
            stmnt.setInt(2, fileInfoId);
            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}