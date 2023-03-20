package me.wizzo.gummymc.database;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Setter {

    private final GummyMC main;

    public Setter(GummyMC main) {
        this.main = main;
    }

    public void vanishOnOff(UUID uuid) {
        String query = "update {table} set Valore=? where UUID=?".replace("{table}", main.getHikariCPSettings().vanishTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            int value;
            if (!main.getDbGetter().isVanished(uuid)) {
                value = 1;
            } else {
                value = 0;
            }
            ps.setInt(1, value);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mentionOnOff(UUID uuid) {
        String query = "update {table} set valore=? where UUID=?".replace("{table}", main.getHikariCPSettings().mentionTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            int value;
            if (!main.getDbGetter().haveMentionEnable(uuid)) {
                value = 1;
            } else {
                value = 0;
            }
            ps.setInt(1, value);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
