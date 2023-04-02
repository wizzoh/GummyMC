package me.wizzo.gummymc.database;

import me.wizzo.gummymc.GummyMC;

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

    public void createWarps(String name, String NameNoColor, String world, String x, String y, String z, String yaw, String pitch, String members) {
        String query = "insert into {table} (Name,NameNoColor,World,X,Y,Z,Yaw,Pitch,Members) values (?,?,?,?,?,?,?,?,?)".replace("{table}", main.getHikariCPSettings().warpsListTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, NameNoColor);
            ps.setString(3, world);
            ps.setString(4, x);
            ps.setString(5, y);
            ps.setString(6, z);
            ps.setString(7, yaw);
            ps.setString(8, pitch);
            ps.setString(9, members);
            ps.executeUpdate();
            /*for (int a = 0; a < members.length(); a++) {
                String[] b = members.split("-");

            }*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWarps(String NameNoColor) {
        String query = "delete from {table} where NameNoColor=?".replace("{table}", main.getHikariCPSettings().warpsListTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, NameNoColor);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
