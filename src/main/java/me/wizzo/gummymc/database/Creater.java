package me.wizzo.gummymc.database;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Creater {

    private final GummyMC main;

    public Creater(GummyMC main) {
        this.main = main;
    }

    public void createTables() {
        String query = "create table if not exists {table} (Name CHAR(20) not null primary key, UUID CHAR(50) not null, Valore integer not null default 0);".replace("{table}", main.getHikariCPSettings().vanishTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        String query = "replace into  {table} (Name,UUID,Valore) values (?,?,?);".replace("{table}", main.getHikariCPSettings().vanishTable);
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
}
