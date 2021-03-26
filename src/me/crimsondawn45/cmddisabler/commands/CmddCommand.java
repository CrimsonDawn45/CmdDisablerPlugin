package me.crimsondawn45.cmddisabler.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.crimsondawn45.cmddisabler.CmdDisabler;
import me.crimsondawn45.cmddisabler.util.DataFile;

public class CmddCommand implements CommandExecutor {

    private CmdDisabler plugin;

    public CmddCommand(CmdDisabler plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        DataFile commands = this.plugin.commands;
        FileConfiguration config = commands.getConfig();

        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(player.isOp() || player.hasPermission("cmddisabler.manage")) {
                handleCommand(sender, args, plugin);
                return true;

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("permission-error-message")));
                return true;
            }

        } else {
            handleCommand(sender, args, plugin);
            return true;
        }
    }

    //Handle command after permission checks
    private static void handleCommand(CommandSender sender, String[] args, CmdDisabler plugin) {

        DataFile commands = plugin.commands;
        FileConfiguration config = commands.getConfig();

        //Check if there is a command string
        if(args[1] != null && args[1] != "") {

            String option = args[0].toLowerCase();
            String command = args[1].toLowerCase();

            switch(option) {
                case("enable"):
                    enable(sender, command, plugin);
                    break;
                case("disable"):
                    disable(sender, command, plugin);
                    break;
                default:
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("invalid-option-error-message").replace("%option%", option).replace("%command%", command)));
                    break;
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("invalid-command-error-message")));
        }
    }

    private static void enable(CommandSender sender, String command, CmdDisabler plugin) {

        DataFile commands = plugin.commands;
        FileConfiguration config = commands.getConfig();

        if(config.contains("disabled-commands")) {

            List<String> disabledCommandList = config.getStringList("disabled-commands");

            if(disabledCommandList.contains(command)) {
                disabledCommandList.remove(command);
                config.set("disabled-commands", disabledCommandList);
                commands.save(config);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("enabled-success-message").replace("%command%", command)));

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("not-disabled-error-message").replace("%command%", command)));
            }

        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("not-disabled-error-message").replace("%command%", command)));
        }
    }

    private static void disable(CommandSender sender, String command, CmdDisabler plugin) {

        DataFile commands = plugin.commands;
        FileConfiguration config = commands.getConfig();

        if(config.contains("disabled-commands")) {

            List<String> disabledCommandList = config.getStringList("disabled-commands");

            if(!disabledCommandList.contains(command)) {
                disabledCommandList.add(command);
                config.set("disabled-commands", disabledCommandList);
                commands.save(config);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("disabled-success-message").replace("%command%", command)));

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("already-disabled-error-message").replace("%command%", command)));
            }

        } else {
            List<String> disabledCommandList = new ArrayList<String>();
            disabledCommandList.add(command);
            config.set("disabled-commands", disabledCommandList);
            commands.save(config);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("disabled-success-message").replace("%command%", command)));
        }
    }
}
