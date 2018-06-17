package com.nd.nit.dao;

import com.nd.nit.models.Version;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class VersionDao {
    private final Connection con;
    private static final String VERSION_TABLE_NAME = "version";

    public VersionDao(Connection con) {
        this.con = con;
    }

    public void create(Version version) {
        try (PreparedStatement stmnt = con.prepareStatement("INSERT INTO " + VERSION_TABLE_NAME +
                " (create_date, released, description) VALUES (?, ?, ?);")) {
            stmnt.setString(1, LocalDateTime.now().toString());
            stmnt.setBoolean(2, false);
            stmnt.setString(3, version.getDescription());
            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Version update(int id, Version version) {
        try (PreparedStatement stmnt =
                     con.prepareStatement("UPDATE " + VERSION_TABLE_NAME + " SET create_date=?, released=?, description=? " +
                             "WHERE id=?")) {
            stmnt.setString(1, LocalDateTime.now().toString());
            stmnt.setBoolean(2, false);
            stmnt.setString(3, version.getDescription());
            stmnt.setInt(4, id);
            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Version get(int id) {
        try (PreparedStatement stmnt = con.prepareStatement("SELECT * FROM version WHERE id=?")) {
            stmnt.setInt(1, id);
            ResultSet resultSet = stmnt.executeQuery();
            resultSet.next();
            Version version = new Version();
            version.setId(resultSet.getInt("id"));
            version.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());
            version.setReleased(resultSet.getBoolean("released"));
            version.setDescription(resultSet.getString("description"));

            return version;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Version> getAll() {
        List<Version> versionList = new ArrayList<>();
        try (PreparedStatement stmnt = con.prepareStatement("SELECT * FROM version")) {
            ResultSet resultSet = stmnt.executeQuery();
            while (resultSet.next()) {
                Version version = new Version();
                version.setId(resultSet.getInt("id"));
                version.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());
                version.setReleased(resultSet.getBoolean("released"));
                version.setDescription(resultSet.getString("description"));
                versionList.add(version);
            }

            return versionList;
        } catch (SQLException e) {
            return null;
        }
    }
}
