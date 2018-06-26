package com.nd.nit.dao;

import com.nd.nit.models.FileBinaryModel;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;

public class FileBinaryDao {
    private final Connection con;
    private static final String FILE_TABLE_NAME = "file_binary";

    public FileBinaryDao(Connection con) {
        this.con = con;
    }

    public int create(FileBinaryModel file) throws SQLException {
        try (PreparedStatement stmnt = con.prepareStatement(
                "INSERT INTO " + FILE_TABLE_NAME +
                " (content, hash_content) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmnt.setBinaryStream(1, new ByteArrayInputStream(file.getContent()));
            stmnt.setString(2, file.getHashContent());
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            if (rs.next()) {
                int lastId = rs.getInt(1);
                return lastId;
            }
        }
        return 0;
    }

    public FileBinaryModel get(int id) {
        try (PreparedStatement stmnt = con.prepareStatement(
                "SELECT * FROM " + FILE_TABLE_NAME + " WHERE id=?")) {
            stmnt.setInt(1, id);
            ResultSet resultSet = stmnt.executeQuery();
            resultSet.next();
            FileBinaryModel file = new FileBinaryModel();
            file.setId(resultSet.getInt("id"));
            file.setContent(IOUtils.toByteArray(resultSet.getBinaryStream("content")));
            file.setHashContent(resultSet.getString("hash_content"));

            return file;
        } catch (SQLException | IOException e) {
            return null;
        }
    }
}