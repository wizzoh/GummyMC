package me.wizzo.gummymc.database;

import me.wizzo.gummymc.GummyMC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Getter {

    private final GummyMC main;

    public Getter(GummyMC main) {
        this.main = main;
    }


//------------------- VANISH SECTION -----------------------
    public boolean isNotIntoVanishTables(UUID uuid) {
        String query = "select * from {table} where UUID=?".replace("{table}", main.getHikariCPSettings().vanishTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            return !results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isVanished(UUID uuid) {
        String query = "select Valore from {table} where UUID=?".replace("{table}", main.getHikariCPSettings().vanishTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return results.getInt("Valore") == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getPlayerVanishedList() {
        String query = "select * from {table} where Valore=1".replace("{table}", main.getHikariCPSettings().vanishTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            List<String> playerVanishedList = new LinkedList<>();
            ResultSet results = ps.executeQuery();
            //playerVanishedList.clear();
            while (results.next()) {
                String playerName = results.getString("Name");
                playerVanishedList.add(playerName);
            }
            return playerVanishedList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//------------------ MENTION SECTION ----------------

    public boolean isNotIntoMentionTables(UUID uuid) {
        String query = "select * from {table} where UUID=?".replace("{table}", main.getHikariCPSettings().mentionTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            return !results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean haveMentionEnable(UUID uuid) {
        String query = "select Valore from {table} where UUID=?".replace("{table}", main.getHikariCPSettings().mentionTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return results.getInt("Valore") == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
