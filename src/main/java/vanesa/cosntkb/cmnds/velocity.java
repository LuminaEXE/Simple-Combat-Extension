package vanesa.cosntkb.cmnds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class velocity implements CommandExecutor {

    public static double velocityAmplifier = 0.0;
    public static boolean showHitMessages = false;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("velocity")) {
            if(sender.isOp()) {
                if (args.length != 0) {
                    if (args.length > 2) {

                        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Too Many Arguments /Velocity <amplifier>");

                    } else if (args[0].equalsIgnoreCase("amplify")) {

                        velocityAmplifier = Double.parseDouble(args[1]);
                        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Velocity Has been Updated");

                    } else if (args[0].equalsIgnoreCase("value")) {

                        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Velocity is: " + velocityAmplifier);

                    } else if (args[0].equalsIgnoreCase("showMessages")) {

                        if(!args[1].equalsIgnoreCase("false")){
                            showHitMessages = true;
                            sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Hit Messages are Visible");
                        } else {
                            showHitMessages = false;
                            sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Hit Messages are Invisible");
                        }

                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Not Enough Arguments /Velocity <Amplifier>");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Not an Administrator");
            }
        }
        return true;
    }

}
