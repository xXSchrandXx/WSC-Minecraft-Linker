package de.xxschrandxx.wsc.wsclinker.bungee.listener;

import java.util.Arrays;

import de.xxschrandxx.wsc.wscbridge.bungee.api.command.SenderBungee;
import de.xxschrandxx.wsc.wsclinker.bungee.MinecraftLinkerBungee;
import de.xxschrandxx.wsc.wsclinker.core.MinecraftLinkerVars;
import de.xxschrandxx.wsc.wsclinker.core.commands.WSCLinker;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class WSCLinkerCommandAliasBungee implements Listener {
    @EventHandler
    public void onCommand(ChatEvent event) {
        MinecraftLinkerBungee instance = MinecraftLinkerBungee.getInstance();
        if (!instance.getConfiguration().getBoolean(MinecraftLinkerVars.Configuration.cmdAliasEnabled)) {
            return;
        }

        if (!event.isCommand()) {
            return;
        }

        String[] split = event.getMessage().split(" ");
        String command = split[0].replaceFirst("/", "");
        if (!instance.getConfiguration().getStringList(MinecraftLinkerVars.Configuration.cmdAliases).contains(command.toLowerCase())) {
            return;
        }
        String[] args = {};
        if (split.length > 0) {
            args = Arrays.copyOfRange(split, 1, split.length);
        }
        if (!(event.getSender() instanceof CommandSender)) {
            return;
        }
        SenderBungee sb = new SenderBungee((CommandSender) event.getSender(), instance);
        new WSCLinker(instance).execute(sb, args);
    }
}
