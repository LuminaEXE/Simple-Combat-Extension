package vanesa.cosntkb.cmnds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class healingCmds implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if(player.isOp()){
            if(command.getName().equalsIgnoreCase("playSound")) {
                if(args.length == 1)
                    player.setHealth(Double.parseDouble(args[0]));
            }

        }
        return true;
    }
}
