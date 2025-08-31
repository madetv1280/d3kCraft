package de.madetv1280.d3kCraft.commands;

import de.madetv1280.d3kCraft.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CraftReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (!sender.hasPermission("d3kcraft.reload.use")) {
            sender.sendMessage("no-permission"); // Check for permission


            return true;
        }

        Main.getInstance().reloadMessages(); // Reload config
        sender.sendMessage(Main.getInstance().getMessage("plugin-reloaded"));
        return true;
    }
}
