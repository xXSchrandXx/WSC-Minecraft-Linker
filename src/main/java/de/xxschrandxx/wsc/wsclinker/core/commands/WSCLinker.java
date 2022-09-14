package de.xxschrandxx.wsc.wsclinker.core.commands;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import de.xxschrandxx.wsc.wscbridge.core.IMinecraftBridgePlugin;
import de.xxschrandxx.wsc.wscbridge.core.api.Response;
import de.xxschrandxx.wsc.wscbridge.core.api.command.ISender;
import de.xxschrandxx.wsc.wsclinker.core.MinecraftLinkerVars;
import de.xxschrandxx.wsc.wsclinker.core.api.IMinecraftLinkerCoreAPI;

public class WSCLinker {
    private IMinecraftBridgePlugin<? extends IMinecraftLinkerCoreAPI> instance;
    public WSCLinker(IMinecraftBridgePlugin<? extends IMinecraftLinkerCoreAPI> instance) {
        this.instance = instance;
    }
    public void execute(ISender<?> sender, String[] args) {
        if (args.length == 0) {
            player(sender);
        }
        else {
            admin(sender, args);
        }
    }

    public void player(ISender<?> sender) {
        if (!sender.isPlayer()) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdPlayerOnly);
            return;
        }
        if (!sender.checkPermission(MinecraftLinkerVars.Configuration.PermCmdWSCLinker)) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdNoPerm);
            return;
        }
        UUID uuid = sender.getUniqueId();
        if (uuid == null) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdPlayerOnly);
            return;
        }

        Response<String, Object> response = null;
        try {
            response = instance.getAPI().sendCode(uuid, sender.getName());
        } catch (IOException e) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdError);
            instance.getLogger().log(Level.INFO, "Could not send/read request for UUID " + uuid.toString(), e);
            return;
        }
        if (response.getResponse() == null) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdError);
            instance.getLogger().log(Level.INFO, "Could not send/read request for UUID " + uuid.toString() + " response is null.");
            return;
        }
        Object codeObject = response.get("code");
        if (codeObject == null) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdError);
            instance.getLogger().log(Level.INFO, "Could not send/read request for UUID " + uuid.toString() + " response has no code.");
            return;
        }
        if (!(codeObject instanceof String)) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdError);
            instance.getLogger().log(Level.INFO, "Could not send/read request for UUID " + uuid.toString() + " code is no string.");
            return;
        }
        String code = (String) codeObject;
        if (code.isBlank()) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdAlreadyLinked);
            return;
        }
        String text = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdCreatedText).replaceAll("%code%", code);
        String hover = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdCreatedHover);
        sender.sendMessage(text, hover, code);
    }

    public void admin(ISender<?> sender, String[] args) {
        if (!sender.checkPermission(MinecraftLinkerVars.Configuration.PermCmdWSCLinkerAdmin)) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdNoPerm);
            return;
        }
        ISender<?> target = null;
        try {
            UUID uuid = UUID.fromString(args[0]);
            target = instance.getAPI().getSender(uuid, instance);
            }
        catch (IllegalArgumentException e) {
            target = instance.getAPI().getSender(args[0], instance);
        }
        if (target == null) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdAdminNoPlayer);
            return;
        }
        Response<String, Object> response = null;
        try {
            response = instance.getAPI().sendCode(target.getUniqueId(), target.getName());
        } catch (IOException e) {
            String text = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdAdminError);
            sender.sendMessage(text.replaceAll("%error%", e.getMessage()));
            return;
        }
        if (response.getResponse() == null) {
            String text = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdAdminError);
            sender.sendMessage(text.replaceAll("%error%", "Could not read response"));
            return;
        }
        Object codeObject = response.get("code");
        if (codeObject == null) {
            String text = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdAdminError);
            sender.sendMessage(text.replaceAll("%error%", "Response does not contain 'code'"));
            return;
        }
        if (!(codeObject instanceof String)) {
            String text = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdAdminError);
            sender.sendMessage(text.replaceAll("%error%", "Code is not a String"));
            return;
        }
        String code = (String) codeObject;
        if (code.isBlank()) {
            sender.send(MinecraftLinkerVars.Configuration.LangCmdAlreadyLinked);
            return;
        }
        String text = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdAdminSuccessText).replaceAll("%code%", code).replaceAll("%name%", target.getName());
        String hover = instance.getConfiguration().getString(MinecraftLinkerVars.Configuration.LangCmdAdminSuccessHover);
        sender.sendMessage(text, hover, code);
    }
}
