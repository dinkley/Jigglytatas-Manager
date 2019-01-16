package com.dinkley.jigglytatas;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class JigglytatasConsoleSay implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
         if(commandSender instanceof ConsoleCommandSender)
         {

         }
         return true;
    }
}
