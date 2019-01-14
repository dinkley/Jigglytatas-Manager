package com.dinkley.jigglytatas;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult.*;

public class JigglytatasListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE +
                "[Jigglytatas Server]" + ChatColor.BLUE +
                " Welcome to the Server!");
        event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE +
                "[Jigglytatas Server]" + ChatColor.GOLD +
                " keepInventory is currently " + ChatColor.BOLD +
                "OFF");
        event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE +
                "[Jigglytatas Server]" + ChatColor.BLUE +
                " Reminder: Felix is Gay");
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event)
    {
        Player player = event.getEntity().getPlayer();
        Long xPos = Math.round(player.getLocation().getX());
        Long yPos = Math.round(player.getLocation().getY());
        Long zPos = Math.round(player.getLocation().getZ());
        World world = player.getWorld();

        Bukkit.getLogger().info(player.getName() + " has died at: " + xPos + " " + yPos + " " + zPos + " in dimension: " + world.getName());
        player.sendMessage(ChatColor.RED + "Death Position: " + ChatColor.BLUE + xPos + " " + yPos + " " + zPos + " in dimension: " + world.getName());
    }



    @EventHandler
    public void onSleep(PlayerBedEnterEvent event)
    {
        World world = event.getPlayer().getWorld();
        Player player = event.getPlayer();
        PlayerBedEnterEvent.BedEnterResult bedEnterResult = event.getBedEnterResult();
        boolean isNight = (world.getTime() >= 12516);
        //Add 1 to total sleeping players, as arrays start counting from 0
        int sleepingPlayers = getSleepingPlayers(event) + 1;
        int totalPlayers = Bukkit.getOnlinePlayers().size();

        if (isNight && bedEnterResult == OK)
        {
            //Inform server of player sleeping, and their percentage
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC +
                    "[Jigglytatas Server] " + ChatColor.RESET + "" + ChatColor.GOLD +
                    player.getDisplayName() + " is sleeping. [" + sleepingPlayers +
                    "/" + totalPlayers + "]");
            if (sleepingPlayers >= totalPlayers / 2)
            {
                //Inform server that night has been skipped, as threshold has been reached
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC +
                        "[Jigglytatas Server] " + ChatColor.RESET + ""  + ChatColor.GOLD +
                        "50% of players are now sleeping. Skipping night.");
                //Set time to 450 ticks (Minecraft Sunrise)
                world.setTime(450);
            }
        }

        else if (bedEnterResult != OK)
        {
            switch (bedEnterResult)
            {
                case NOT_SAFE: sendJigglyMessageToPlayer(player, "It's not safe to sleep!", true);
                return;
                case TOO_FAR_AWAY: sendJigglyMessageToPlayer(player, "You're too far away from the bed!", true);
                return;
                case NOT_POSSIBLE_NOW: sendJigglyMessageToPlayer(player, "It's too early to sleep!", true);
                return;
                case NOT_POSSIBLE_HERE: sendJigglyMessageToPlayer(player, "You can't sleep here!", true);
                return;
                case OTHER_PROBLEM: sendJigglyMessageToPlayer(player, "You can't sleep! (Unknown Error)", true);
                return;
            }
        }
    }

    public void sendJigglyMessageToPlayer(Player player, String message, boolean isError)
    {
        if (isError)
        {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC  +
                    "[Jigglytatas Server] " + ChatColor.RESET + "" + ChatColor.RED + message);
        }
        else
        {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC  +
                    "[Jigglytatas Server] " + ChatColor.RESET + "" + ChatColor.GOLD + message);
        }

    }

    public int getSleepingPlayers(Event event)
    {
        int sleepingPlayers = 0;
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if (player.isSleeping())
            {
                sleepingPlayers++;
            }
        }
        return sleepingPlayers++;
    }


}