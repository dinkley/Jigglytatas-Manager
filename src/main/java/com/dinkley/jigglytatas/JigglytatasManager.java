package com.dinkley.jigglytatas;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class JigglytatasManager extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        getLogger().info("Ready!");

        // Initialize listener
        getServer().getPluginManager().registerEvents(new JigglytatasListener(), this);

    }

    @Override
    public void onDisable()
    {
        getLogger().info("Shutting down");
    }
}
