package vanesa.cosntkb.cmnds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class giveItems implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(player.isOp()){
            if(command.getName().equalsIgnoreCase("giveItems")) {
                if(command.getName().equalsIgnoreCase("giveItem")) {
                    if(args.length == 1) {
                        if(args[0].equalsIgnoreCase("Apple")) {
                            ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
                            ItemMeta itemMeta = item.getItemMeta();
                            itemMeta.setDisplayName(ChatColor.GOLD + "~Angels Apple~");
                            itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "A Golden Apple that has been blessed by the Angels"));
                        }
                    }
                }
            }
        }
        return false;
    }
}
