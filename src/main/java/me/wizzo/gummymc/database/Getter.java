package me.wizzo.gummymc.database;

import me.wizzo.gummymc.GummyMC;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

//------------------ WARPS SECTION ----------------

    public boolean warpExists(String nameNoColor) {
        String query = "select * from {table} where NameNoColor=?".replace("{table}", main.getHikariCPSettings().warpsListTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nameNoColor);
            ResultSet results = ps.executeQuery();
            return results.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void warpTeleport(Player player, String name) {
        String query = "select * from {table} where NameNoColor=?".replace("{table}", main.getHikariCPSettings().warpsListTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                String warpName = results.getString(1);
                String worldName = results.getString(3);
                double warpX = Double.parseDouble(results.getString(4));
                double warpY = Double.parseDouble(results.getString(5));
                double warpZ = Double.parseDouble(results.getString(6));
                float warpYaw = Float.parseFloat(results.getString(7));
                float warpPitch = Float.parseFloat(results.getString(8));
                String membri = results.getString(9);
                Location location = new Location(main.getServer().getWorld(worldName), warpX, warpY, warpZ, warpYaw, warpPitch);

                if (main.getServer().getWorlds().contains(main.getServer().getWorld(worldName))) {
                    player.teleport(location);
                    player.sendMessage(main.getConfig("GummyMC.Command.Warp.Teleport.Success")
                            .replace("{warpName}", warpName)
                            .replace("{warpAuthors}", String.join(",", membri))
                    );
                } else {
                    player.sendMessage(main.getConfig("GummyMC.Command.Warp.Teleport.World-not-found")
                            .replace("{worldName}", worldName)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getWarpName(String name) {
        String query = "select * from {table} where NameNoColor=?".replace("{table}", main.getHikariCPSettings().warpsListTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return results.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getWarpsMembers(String name) {
        String query = "select * from {table} where NameNoColor=?".replace("{table}", main.getHikariCPSettings().warpsListTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, name);
            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return String.join(",", results.getString(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getPlayerWarpsList(OfflinePlayer player) {
        String query = "select * from {table}".replace("{table}", main.getHikariCPSettings().warpsListTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            String playerName;

            if (player.isOnline()) {
                playerName = player.getPlayer().getName();
            } else {
                playerName = player.getName();
            }
            //String playerName = player.getName();
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                if (results.getString(9).contains(playerName)) {
                    main.getWarpsName().add(results.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllWarpsList() {
        String query = "select * from {table}".replace("{table}", main.getHikariCPSettings().warpsListTable);
        try (Connection connection = main.getHikariCPSettings().getSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                main.getWarpsName().add(results.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
