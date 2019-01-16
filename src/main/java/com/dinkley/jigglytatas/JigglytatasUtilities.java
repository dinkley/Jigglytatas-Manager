package com.dinkley.jigglytatas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class JigglytatasUtilities
{
    public static void sendJigglyMessageToPlayer(Player player, String message, boolean isError)
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

    public static void sendJigglyMessageToServer(String message)
    {
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC  +
                "[Jigglytatas] " + ChatColor.RESET + "" + ChatColor.GOLD + message);


    }

    public static int getSleepingPlayers(Event event)
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
