package com.nd.nit.dao;

import com.nd.nit.models.VersionModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class VersionDao {
    private final Connection con;
    private static final String VERSION_TABLE_NAME = "version";

    public VersionDao(Connection con) {
        this.con = con;
    }

    public int create(VersionModel version) throws SQLException {
        try (PreparedStatement stmnt = con.prepareStatement("INSERT INTO " + VERSION_TABLE_NAME +
                " (create_date, released, description) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmnt.setString(1, LocalDateTime.now().toString());
            stmnt.setBoolean(2, false);
            stmnt.setString(3, version.getDescription());
            stmnt.executeUpdate();

            ResultSet rs = stmnt.getGeneratedKeys();
            if (rs.next()) {
                int lastId = rs.getInt(1);
                return lastId;
            }
        }
        return 0;
    }

    public VersionModel update(int id, VersionModel version) throws SQLException {
        try (PreparedStatement stmnt =
                     con.prepareStatement("UPDATE " + VERSION_TABLE_NAME +
                             " SET create_date=?, released=?, description=? " +
                             "WHERE id=?")) {
            stmnt.setString(1, LocalDateTime.now().toString());
            stmnt.setBoolean(2, version.isReleased());
            stmnt.setString(3, version.getDescription());
            stmnt.setInt(4, id);
            stmnt.executeUpdate();
        }

        return null;
    }

    public VersionModel get(int id) throws SQLException {
        try (PreparedStatement stmnt = con.prepareStatement("SELECT * FROM version WHERE id=? and released=1")) {
            stmnt.setInt(1, id);
            ResultSet resultSet = stmnt.executeQuery();
            if (resultSet.next()) {
                VersionModel version = new VersionModel();
                version.setId(resultSet.getInt("id"));
                version.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());
                version.setReleased(resultSet.getBoolean("released"));
                version.setDescription(resultSet.getString("description"));

                return version;
            } else {
                return null;
            }
        }
    }

    public List<VersionModel> getAll() throws SQLException {
        List<VersionModel> versionList = new ArrayList<>();
        try (PreparedStatement stmnt = con.prepareStatement("SELECT * FROM version")) {
            ResultSet resultSet = stmnt.executeQuery();
            while (resultSet.next()) {
                VersionModel version = new VersionModel();
                version.setId(resultSet.getInt("id"));
                version.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());
                version.setReleased(resultSet.getBoolean("released"));
                version.setDescription(resultSet.getString("description"));
                versionList.add(version);
            }

            return versionList;
        }
    }

    public VersionModel getLast() throws SQLException {
        try (PreparedStatement stmnt = con.prepareStatement("SELECT * FROM version WHERE released=1 " +
                "ORDER BY id DESC LIMIT 1")) {
            ResultSet resultSet = stmnt.executeQuery();
            if (resultSet.next()) {
                VersionModel version = new VersionModel();
                version.setId(resultSet.getInt("id"));
                version.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());
                version.setReleased(resultSet.getBoolean("released"));
                version.setDescription(resultSet.getString("description"));

                return version;
            } return null;
        }
    }
}
