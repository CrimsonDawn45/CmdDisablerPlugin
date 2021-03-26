package me.crimsondawn45.cmddisabler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.crimsondawn45.cmddisabler.commands.CmddCommand;
import me.crimsondawn45.cmddisabler.events.CmdPreProcessEvent;
import me.crimsondawn45.cmddisabler.util.DataFile;

public class CmdDisabler extends JavaPlugin {

    public DataFile commands;

    @Override
    public void onEnable() {

        //Initialize datafile
        this.commands = new DataFile("commands", this);

        //Save default config
        this.saveDefaultConfig();

        //Register Event
        getServer().getPluginManager().registerEvents(new CmdPreProcessEvent(this), this);
    }

    @Override
    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        CmddCommand cmddCommand = new CmddCommand(this);

        if(isCommand(label, "cmdd")) {
            return cmddCommand.onCommand(sender, command, label, args);
        }
        return false;
    }

    private boolean isCommand(String label, String command) {
        return label.toLowerCase().startsWith(command);
    }
}
