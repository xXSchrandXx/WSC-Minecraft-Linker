package de.xxschrandxx.wsc.wsclinker.hytale.commands;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;

import de.xxschrandxx.wsc.wscbridge.hytale.api.command.SenderHytale;
import de.xxschrandxx.wsc.wsclinker.core.commands.WSCLinker;
import de.xxschrandxx.wsc.wsclinker.hytale.HytaleLinker;

public class WSCLinkerHytale extends AbstractCommand {

    public WSCLinkerHytale(List<String> aliases) {
        super("wsclinker", "WSCLinker command");
        if (aliases != null) {
            for (String alias : aliases) {
                addAliases(alias);
            }
        }
        setAllowsExtraArguments(true);
    }

    @Override
    @Nullable
    protected CompletableFuture<Void> execute(@Nonnull CommandContext ctx) {
        HytaleLinker instance = HytaleLinker.getInstance();
        SenderHytale s = new SenderHytale(ctx.sender(), instance);
        String[] args = ctx.getInputString().split(" ");
        String[] argsWithoutFirst = Arrays.copyOfRange(args, 1, args.length);
        new WSCLinker(instance).execute(s, argsWithoutFirst);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected boolean canGeneratePermission() {
        return false;
    }
}
