package dev.weary.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import dev.weary.items.CustomItemManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("detector")
public class DetectorCommand extends BaseCommand {

    @Subcommand("give")
    @CommandPermission("detector.give")
    @CommandCompletion("basic|super")
    public void onGiveCommand(Player player, String type) {
        if (type.equals("basic")) {
            ItemStack basicDetector = CustomItemManager.getItem("scouting_device").createNewItem();
            player.getInventory().addItem(basicDetector);
            player.sendMessage("Basic Scouting Device was added to your inventory.");
        }
        else if (type.equals("super")) {
            ItemStack superDetector = CustomItemManager.getItem("super_scouting_device").createNewItem();
            player.getInventory().addItem(superDetector);
            player.sendMessage("Super Scouting Device was added to your inventory.");
        }
        else {
            player.sendMessage("Please specify a valid type.");
        }
    }

    @HelpCommand
    @CatchUnknown @Default
    public void onHelp(CommandSender player, CommandHelp help) {
        help.showHelp();
    }
}
