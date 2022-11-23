package vanesa.cosntkb.cmnds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import vanesa.cosntkb.sex;

public class healing implements Listener {

    public static boolean onCooldown = false;
    public static double cooldownTime = 10.0;

    private final sex plugin;
    public healing(sex plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void healPlayer(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();


        if ((action == Action.PHYSICAL) || (event.getItem() == null) || (event.getItem().getType() == Material.GHAST_TEAR)) {
            // Check if item is stick && Named "~Angel's Tear~"
            if (event.getItem().getItemMeta().getDisplayName().equals("~Angel's Tear~")) {
                if(!onCooldown) {
                    //Revival(Extra Health Boost)
                    if (player.getHealth() < 6) {

                        player.setHealth(player.getHealth() + 12);
                        player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "REVIVAL!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);

                        if(cooldownTime == 10.0) {
                            cooldownTime = 17.5; // <~~ Penalty for using Revival

                            //Cooldown Logic
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Angel's Tear Cooldown Ended");
                                    onCooldown = false;
                                    cooldownTime = 10.0;
                                }
                            }.runTaskLater(this.plugin, (long) (cooldownTime * 20));
                        }
                        else {
                            //Cooldown Logic
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Angel's Tear Cooldown Ended");
                                    onCooldown = false;
                                }
                            }.runTaskLater(this.plugin, (long) (cooldownTime * 20));
                        }
                    } else if (Math.round(player.getHealth()) + 5 > 20) {
                        player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Healed!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.setHealth(Math.round(player.getHealth()) + (20 - Math.round(player.getHealth())));
                    } else {
                        player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Healed!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.setHealth(player.getHealth() + 5);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "Angel's Tear is on cooldown!");
                }

            }
        }
    }
}
