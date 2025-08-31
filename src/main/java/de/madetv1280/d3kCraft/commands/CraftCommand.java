package de.madetv1280.d3kCraft.commands;

import de.madetv1280.d3kCraft.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CraftCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Check if sender has Permission
        if (!sender.hasPermission("d3kcraft.craft.use")) {
            boolean opRequired = Main.getInstance().getConfig().getBoolean("craft-op-required");

            // Is op neeeded?
            if (opRequired && !sender.isOp()) {
                sender.sendMessage(Main.getInstance().getMessage("no-permission"));
                return true;
            }

            // OP isn't needed, but no permission
            if (!opRequired) {
                sender.sendMessage(Main.getInstance().getMessage("no-permission"));
                return true;
            }
        }

        // Is sender a player?
        if (sender instanceof Player player) {
            player.openWorkbench(null, true); // open 3x3 Crafting Inventory
            return true;
        } else {
            sender.sendMessage(Main.getInstance().getMessage("only-players"));
            return true;
        }

    }
}
