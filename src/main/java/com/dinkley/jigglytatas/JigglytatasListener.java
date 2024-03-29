package com.dinkley.jigglytatas;
import com.sun.xml.internal.bind.v2.TODO;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


import static org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult.*;

public class JigglytatasListener implements Listener
{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        event.setJoinMessage(null);
        sendJigglyMessageToServer(player.getDisplayName() + " has joined the server.");

        event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE +
                "[Jigglytatas]" + ChatColor.BLUE +
                " Welcome to the Server!");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        event.setQuitMessage(null);
        sendJigglyMessageToServer(player.getDisplayName() + " has left the server.");
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event)
    {
        Player player = event.getPlayer();

        event.setLeaveMessage(null);
        if (event.getReason() != null)
        {
            sendJigglyMessageToServer(player.getDisplayName() + " has been kicked from the server. Reason: " + event.getReason());
        }
        else
        {
            sendJigglyMessageToServer(player.getDisplayName() + " has been kicked from the server.");
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
        sendJigglyMessageToPlayer(player, "Death Position: " + xPos + " " + yPos + " " + zPos + " in dimension: " + world.getName(), false);
    }


    @EventHandler
    public void entityDeath(EntityDeathEvent event)
    {
        Entity entity = event.getEntity();
        if(entity.getType().toString() == "ENDER_DRAGON")
        {
            sendJigglyMessageToServer(((LivingEntity) entity).getKiller().getDisplayName() + " has defeated the Ender Dragon.");
        }
        if(entity.getType().toString() == "WITHER")
        {
            sendJigglyMessageToServer(((LivingEntity) entity).getKiller().getDisplayName() + " has defeated the Wither.");
        }
        else
        {
            return;
        }

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
        float halfPlayers = totalPlayers / 2;

        if (isNight && bedEnterResult == OK)
        {
            //Inform server of player sleeping, and their percentage
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "[Jigglytatas] " + ChatColor.RESET + "" + ChatColor.GOLD + player.getDisplayName() + " is sleeping. [" + sleepingPlayers + "/" + totalPlayers + "]");

            if (sleepingPlayers >= halfPlayers)
            {

                //Inform server that night has been skipped, as threshold has been reached
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "[Jigglytatas] " + ChatColor.RESET + ""  + ChatColor.GOLD + "At least 50% of players are now sleeping. Skipping night.");

                //Set time to 450 ticks (Minecraft Sunrise)
                world.setTime(450);
            }
        }

        else if (bedEnterResult != OK)
        {
            switch (bedEnterResult)
            {
                //All results of bedEnterResult
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
                    "[Jigglytatas] " + ChatColor.RESET + "" + ChatColor.RED + message);
        }
        else
        {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC  +
                    "[Jigglytatas] " + ChatColor.RESET + "" + ChatColor.GOLD + message);
        }

    }

    public void sendJigglyMessageToServer(String message)
    {
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC  +
                "[Jigglytatas] " + ChatColor.RESET + "" + ChatColor.GOLD + message);


    }

    public int getSleepingPlayers(Event event)
    {
        int sleepingPlayers = 0;
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if (player.isSleeping())
            {
                //Increment sleepingPlayers by one, then move onto the next player
                sleepingPlayers++;
            }
        }
        return sleepingPlayers++;
    }

}
