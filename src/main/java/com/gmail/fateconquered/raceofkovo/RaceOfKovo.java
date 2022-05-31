package com.gmail.fateconquered.raceofkovo;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class RaceOfKovo extends JavaPlugin {
    public final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        this.logger.info(String.valueOf(pdfFile.getName())
                + " остановлен!");
    }

    @Override
    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        this.logger.info(String.valueOf(pdfFile.getName()) + " версии "
                + pdfFile.getVersion() + " успешно запущен.");
        PluginManager pm = getServer().getPluginManager();
        getServer().getPluginManager().registerEvents(new Effects(), this);
        getServer().getPluginManager().registerEvents(new ToadJump(), this);
    }

}