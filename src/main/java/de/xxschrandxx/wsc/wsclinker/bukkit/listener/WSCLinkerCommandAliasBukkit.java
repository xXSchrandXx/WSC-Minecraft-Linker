package de.xxschrandxx.wsc.wsclinker.bukkit.listener;

import java.util.Arrays;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import de.xxschrandxx.wsc.wscbridge.bukkit.api.command.SenderBukkit;
import de.xxschrandxx.wsc.wsclinker.bukkit.MinecraftLinkerBukkit;
import de.xxschrandxx.wsc.wsclinker.core.LinkerVars;
import de.xxschrandxx.wsc.wsclinker.core.commands.WSCLinker;

public class WSCLinkerCommandAliasBukkit implements Listener {
    @EventHandler
    public void onCommand(ServerCommandEvent event) {
        if (event.isCancelled()) {
            return;
        }
        MinecraftLinkerBukkit instance = MinecraftLinkerBukkit.getInstance();
        if (!instance.getConfiguration().getBoolean(LinkerVars.Configuration.cmdAliasEnabled)) {
            return;
        }

        String[] split = event.getCommand().split(" ");
        String command = split[0];
        if (!instance.getConfiguration().getStringList(LinkerVars.Configuration.cmdAliases).contains(command.toLowerCase())) {
            return;
        }
        String[] args = {};
        if (split.length > 0) {
            args = Arrays.copyOfRange(split, 1, split.length);
        }

        SenderBukkit sb = new SenderBukkit(event.getSender(), instance);
        new WSCLinker(instance).execute(sb, args);
        event.setCancelled(true);
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }
        MinecraftLinkerBukkit instance = MinecraftLinkerBukkit.getInstance();
        if (!instance.getConfiguration().getBoolean(LinkerVars.Configuration.cmdAliasEnabled)) {
            return;
        }

        String[] split = event.getMessage().split(" ");
        String command = split[0].replaceFirst("/", "");
        if (!instance.getConfiguration().getStringList(LinkerVars.Configuration.cmdAliases).contains(command.toLowerCase())) {
            return;
        }
        String[] args = {};
        if (split.length > 0) {
            args = Arrays.copyOfRange(split, 1, split.length);
        }

        SenderBukkit sb = new SenderBukkit(event.getPlayer(), instance);
        new WSCLinker(instance).execute(sb, args);
        event.setCancelled(true);
    }
}
