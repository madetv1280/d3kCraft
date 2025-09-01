package de.madetv1280.CraftD3K.commands;

import de.madetv1280.CraftD3K.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CraftCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // --- Check if sender has permission ---
        if (!sender.hasPermission("craftd3k.craft.use")) {

            sender.sendMessage(Main.getInstance().getMessage("no-permission"));
            return true;

        }

        // Check if sender is a player
        if (sender instanceof Player player) {

            int cooldownSeconds = Main.getInstance().getConfig().getInt("cooldown", 0);
            if (cooldownSeconds > 0) {
                long now = System.currentTimeMillis();
                long lastUsed = Main.getInstance().getCooldowns().getOrDefault(player.getUniqueId(), 0L);
                long timeLeft = (lastUsed + (cooldownSeconds * 1000L)) - now;

                if (timeLeft > 0) {
                    if (Main.getInstance().getConfig().getBoolean("cooldown-message-enabled", true)) {
                        String msg = Main.getInstance().getMessage("cooldown-message");

                        msg = msg.replace("{time}", String.valueOf(timeLeft / 1000));

                        player.sendMessage(msg);
                    }
                    return true; // Cooldown active, abort command
                }
            }

            // Open 3x3 crafting inventory
            player.openWorkbench(null, true);
            Main.getInstance().getCooldowns().put(player.getUniqueId(), System.currentTimeMillis()); // set cooldown
            return true;

        } else {
            // Sender is not a player
            sender.sendMessage(Main.getInstance().getMessage("only-players"));
            return true;
        }
    }
}
