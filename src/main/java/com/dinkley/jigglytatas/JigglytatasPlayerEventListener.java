package com.dinkley.jigglytatas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import com.dinkley.jigglytatas.JigglytatasListener;
import org.bukkit.inventory.PlayerInventory;

public class JigglytatasPlayerEventListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        event.setJoinMessage(null);
        JigglytatasUtilities.sendJigglyMessageToServer(player.getDisplayName() + " has joined the server.");

        event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE +
                "[Jigglytatas]" + ChatColor.BLUE +
                " Welcome to the Server!");
        event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE +
                "[Jigglytatas]" + ChatColor.BLUE +
                " Reminder: Felix is Gay");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        event.setQuitMessage(null);
        JigglytatasUtilities.sendJigglyMessageToServer(player.getDisplayName() + " has left the server.");
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
        Player player = event.getPlayer();

        event.setLeaveMessage(null);
        if (event.getReason() != null)
        {
            JigglytatasUtilities.sendJigglyMessageToServer(player.getDisplayName() + " has been kicked from the server. Reason: " + event.getReason());
        }
        else
        {
            JigglytatasUtilities.sendJigglyMessageToServer(player.getDisplayName() + " has been kicked from the server.");
        }
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event)
    {
        Player player = event.getEntity().getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        /*
        for(ItemStack itemStack : playerInventory )
        {
            //TODO: Add items to array
        }
        */

        //Round down coordinates to the nearest full number, to prevent
        //Insane decimal values like 103.219237891273897
        Long xPos = Math.round(player.getLocation().getX());
        Long yPos = Math.round(player.getLocation().getY());
        Long zPos = Math.round(player.getLocation().getZ());
        World world = player.getWorld();


        Bukkit.getLogger().info(player.getName() + " has died at: " + xPos + " " + yPos + " " + zPos + " in dimension: " + world.getName());
        JigglytatasUtilities.sendJigglyMessageToPlayer(player, "Death Position: " + xPos + " " + yPos + " " + zPos + " in dimension: " + world.getName(), false);
    }
}
