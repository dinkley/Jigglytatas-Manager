package com.dinkley.jigglytatas;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class JigglytatasEarlyWarning extends BukkitRunnable
{
    private final JavaPlugin plugin;

    public JigglytatasEarlyWarning(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        plugin.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC  +
                "[Jigglytatas Server] " + ChatColor.RESET + "" + ChatColor.RED + "Corrupted chunks detected near player " + plugin.getServer().getPlayer("iLikeThisOne").getDisplayName() + " Regenerating chunks.");
    }

}
