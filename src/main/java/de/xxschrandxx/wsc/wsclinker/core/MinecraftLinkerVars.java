package de.xxschrandxx.wsc.wsclinker.core;

import java.util.logging.Logger;

import de.xxschrandxx.wsc.wscbridge.core.api.configuration.AbstractConfiguration;
import de.xxschrandxx.wsc.wscbridge.core.api.configuration.IConfiguration;

public class MinecraftLinkerVars extends AbstractConfiguration {
    public static boolean startConfig(IConfiguration<?> configuration, Logger logger) {
        return startConfig(configuration, Configuration.class, defaults.class, logger);
    }

    public static class Configuration {
        // universal
        // url.sendCode
        public static final String urlSendCode ="url.sendCode";
        // url.updateNames
        public static final String urlUpdateNames ="url.updateNames";
        // url.getLinked
        public static final String urlGetLinked ="url.getLinked";
        // url.getUnlinked
        public static final String urlGetUnlinked ="url.getUnlinked";
        // updateNames.enabled
        public static final String updateNamesEnabled = "updateNames.enabled";
        // updateNames.interval
        public static final String updateNamesInterval = "updateNames.interval";

        // unlinkedMessage.enabled
        public static final String unlinkedMessageEnabled = "unlinkedMessage.enabled";
        // unlinkedMessage.interval
        public static final String unlinkedMessageInterval = "unlinkedMessage.interval";

        // permission
        // permission.command.wsclinker
        public static final String PermCmdWSCLinker = "permission.command.wsclinker";
        // permission.command.admin
        public static final String PermCmdWSCLinkerAdmin = "permission.command.admin";

        // language
        // language.command.noperm
        public static final String LangCmdNoPerm = "language.command.noperm";
        // language.command.reload.config.start
        public static final String LangCmdReloadConfigStart = "language.command.reload.config.start";
        // language.command.reload.config.error
        public static final String LangCmdReloadConfigError = "language.command.reload.config.error";
        // language.command.reload.config.success
        public static final String LangCmdReloadConfigSuccess = "language.command.reload.config.success";
        // language.command.reload.api.start
        public static final String LangCmdReloadAPIStart = "language.command.reload.api.start";
        // language.command.reload.api.success
        public static final String LangCmdReloadAPISuccess = "language.command.reload.api.success";
        // language.command.playeronly
        public static final String LangCmdPlayerOnly = "language.command.playeronly";
        // language.command.error
        public static final String LangCmdError = "language.command.error";
        // language.command.alreadylinked
        public static final String LangCmdAlreadyLinked = "language.command.alreadylinked";
        // language.command.created.text
        public static final String LangCmdCreatedText = "language.command.created.text";
        // language.command.created.hover
        public static final String LangCmdCreatedHover = "language.command.created.hover";
        // language.command.edit.text
        public static final String LangCmdEditText = "language.command.edit.text";
        // language.command.edit.hover
        public static final String LangCmdEditHover = "language.command.edit.hover";
        // language.command.admin.noplayer
        public static final String LangCmdAdminNoPlayer = "language.command.admin.noplayer";
        // language.command.admin.alreadylinked
        public static final String LangCmdAdminAlreadyLinked = "language.command.admin.alreadylinked";
        // language.command.admin.error
        public static final String LangCmdAdminError = "language.command.admin.error";
        // language.command.admin.success.text
        public static final String LangCmdAdminSuccessText = "language.command.admin.success.text";
        // language.command.admin.success.hover
        public static final String LangCmdAdminSuccessHover = "language.command.admin.success.hover";
        // language.unlinkedMessage
        public static final String LangUnlinkedMessage = "language.unlinkedMessage";
    }
    // Default values
    public static final class defaults {
        // universal
        // url.sendCode
        public static final String urlSendCode = "https://example.domain/index.php?minecraft-linker-code//";
        // url.updateNames
        public static final String urlUpdateNames = "https://example.domain/index.php?minecraft-linker-update-name/";
        // url.getLinked
        public static final String urlGetLinked = "https://example.domain/index.php?minecraft-linker-get-linked/"; // TODO
        // url.getUnlinked
        public static final String urlGetUnlinked = "https://example.domain/index.php?minecraft-linker-get-unlinked/"; // TODO

        // updateNames.enabled
        public static final Boolean updateNamesEnabled = true;
        // updateNames.interval
        public static final Integer updateNamesInterval = 5;

        // unlinkedMessage.enabled
        public static final Boolean unlinkedMessageEnabled = true;
        // unlinkedMessage.interval
        public static final Integer unlinkedMessageInterval = 5;

        // permission
        // permission.command.wsclinker
        public static final String PermCmdWSCLinker = "wsclinker.command.wsclinker";
        // permission.command.admin
        public static final String PermCmdWSCLinkerAdmin = "wsclinker.command.admin";

        // language
        // language.command.noperm
        public static final String LangCmdNoPerm = "&8[&6WSC-Linker&8]&c You don't have permission to do this.";
        // language.command.playeronly
        public static final String LangCmdPlayerOnly = "&8[&6WSC-Linker&8]&c You need to be a player.";
        // language.command.reload.config.start
        public static final String LangCmdReloadConfigStart = "&8[&6WSC-Linker&8]&7 Reloading configuration.";
        // language.command.reload.config.error
        public static final String LangCmdReloadConfigError = "&8[&6WSC-Linker&8]&e Reloading configuration failed.";
        // language.command.reload.config.success
        public static final String LangCmdReloadConfigSuccess = "&8[&6WSC-Linker&8]&7 Configuration reloaded successfully.";
        // language.command.reload.api.start
        public static final String LangCmdReloadAPIStart = "&8[&6WSC-Linker&8]&7 Reloading API.";
        // language.command.reload.api.success
        public static final String LangCmdReloadAPISuccess = "&8[&6WSC-Linker&8]&7 API reloaded successfully.";
        // language.command.error
        public static final String LangCmdError = "&8[&6WSC-Linker&8]&c An error occurred, contact an admin.";
        // language.command.alreadylinked
        public static final String LangCmdAlreadyLinked = "&8[&6WSC-Linker&8]&c UUID already linked.";
        // language.command.created.text
        public static final String LangCmdCreatedText = "&8[&6WSC-Linker&8]&7 Your code is %code%.";
        // language.command.created.hover
        public static final String LangCmdCreatedHover = "Click to copy code.";
        // language.command.edit.text
        public static final String LangCmdEditText = "&8[&6WSC-Linker&8]&7 Your new code is %code%.";
        // language.command.edit.hover
        public static final String LangCmdEditHover = "Click to copy code.";
        // language.command.admin.noplayer
        public static final String LangCmdAdminNoPlayer = "&8[&6WSC-Linker&8]&c Cannot find player.";
        // language.command.admin.alreadylinked
        public static final String LangCmdAdminAlreadyLinked = "&8[&6WSC-Linker&8]&c UUID already linked.";
        // language.command.admin.error
        public static final String LangCmdAdminError = "&8[&6WSC-Linker&8]&c %error%";
        // language.command.admin.success.text
        public static final String LangCmdAdminSuccessText = "&8[&6WSC-Linker&8]&7 Code for %name% is %code%.";
        // language.command.admin.success.hover
        public static final String LangCmdAdminSuccessHover = "Click to copy code.";
        // language.unlinkedMessage
        public static final String LangUnlinkedMessage = "&8[&6WSC-Linker&8]&7 Link your Minecraft-Account: https://domain.example/index.php?minecraft-user-add/";
    }
}
