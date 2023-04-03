package me.wizzo.gummymc.database;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Creater {

    private final GummyMC main;

    public Creater(GummyMC main) {
        this.main = main;
    }

    public void createTables() {
        String query = ("create table if not exists {table} (Name CHAR(20) not null primary key, UUID CHAR(50) not null, " +
                "Valore integer not null default 0);"
        ).replace("{table}", main.getHikariCPSettings().vanishTable);

        String query2 = (
                "create table if not exists {table} (Name CHAR(20) not null primary key, UUID CHAR(50) not null," +
                " Valore integer not null default 0);"
        ).replace("{table}", main.getHikariCPSettings().mentionTable);

        String query3 = (
                "create table if not exists {table} (Name CHAR(100) not null, NameNoColor CHAR(100) not null primary key, World CHAR(50) not null, " +
                "X CHAR(50) not null, Y CHAR(50) not null, Z CHAR(50) not null, Yaw CHAR(50) not null, Pitch CHAR(50) not null, " +
                "Members LONGTEXT not null);"
        ).replace("{table}", main.getHikariCPSettings().warpsListTable);

        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             PreparedStatement ps2 = connection.prepareStatement(query2);
             PreparedStatement ps3 = connection.prepareStatement(query3)) {
            ps.execute();
            ps2.execute();
            ps3.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayerVanish(Player player) {
        String query = "replace into {table} (Name,UUID,Valore) values (?,?,?);".replace("{table}", main.getHikariCPSettings().vanishTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, player.getName());
            ps.setString(2, player.getUniqueId().toString());
            ps.setInt(3, 0);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayerMention(Player player) {
        String query = "replace into {table} (Name,UUID,Valore) values (?,?,?);".replace("{table}", main.getHikariCPSettings().mentionTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, player.getName());
            ps.setString(2, player.getUniqueId().toString());
            ps.setInt(3, 1);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
