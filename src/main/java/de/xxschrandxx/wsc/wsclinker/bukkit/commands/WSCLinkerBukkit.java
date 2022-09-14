package de.xxschrandxx.wsc.wsclinker.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.xxschrandxx.wsc.wscbridge.bukkit.api.command.SenderBukkit;
import de.xxschrandxx.wsc.wsclinker.bukkit.MinecraftLinkerBukkit;
import de.xxschrandxx.wsc.wsclinker.core.commands.WSCLinker;

public class WSCLinkerBukkit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MinecraftLinkerBukkit instance = MinecraftLinkerBukkit.getInstance();
        SenderBukkit sb = new SenderBukkit(sender, instance);
        new WSCLinker(instance).execute(sb, args);
        return true;
    }
}
