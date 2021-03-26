package me.crimsondawn45.cmddisabler.events;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.crimsondawn45.cmddisabler.CmdDisabler;
import me.crimsondawn45.cmddisabler.util.DataFile;

public class CmdPreProcessEvent implements Listener {

    private CmdDisabler plugin;

    public CmdPreProcessEvent(CmdDisabler plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPreProcess(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();

        if(!player.isOp() || !player.hasPermission("cmddisabler.bypass")) {

            DataFile commands = this.plugin.commands;
            FileConfiguration config = commands.getConfig();
            String commandLabel = event.getMessage().toLowerCase().trim().split(" ")[0].replace("/", "");

            if(config.contains("disabled-commands")) {

                for(String label : config.getStringList("disabled-commands")) {

                    if(label.toLowerCase() == commandLabel) {

                        event.setCancelled(true);

                        if(config.getBoolean("send-disabled-command-message")) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("disabled-command-message")));
                        }
                        return;
                    }
                }
            }
        }
    }
}