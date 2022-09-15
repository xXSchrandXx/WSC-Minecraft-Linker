package de.xxschrandxx.wsc.wsclinker.bungee.commands;

import de.xxschrandxx.wsc.wscbridge.bungee.api.command.SenderBungee;
import de.xxschrandxx.wsc.wsclinker.bungee.MinecraftLinkerBungee;
import de.xxschrandxx.wsc.wsclinker.core.commands.WSCLinker;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class WSCLinkerBungee extends Command {
    public WSCLinkerBungee(String name) {
        super(name);
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        MinecraftLinkerBungee instance = MinecraftLinkerBungee.getInstance();
        SenderBungee sb = new SenderBungee(sender, instance);
        new WSCLinker(instance).execute(sb, args);;
    }
}
