package vanesa.cosntkb.cmnds;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
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


        if ((action == Action.PHYSICAL) || (event.getItem().getType() == Material.GOLDEN_APPLE) && (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "~Angel's Apple~")) && (event.getItem().getItemMeta().getLore().contains(ChatColor.GRAY + "Heals 4 hearts"))) {
            if((action == Action.PHYSICAL) || (event.getItem().getType() == Material.AIR)) {
                return;
            }
            // Check if item is stick && Named "~Angel's Tear~"
            else if (event.getItem().getItemMeta().getDisplayName().equals("~Angel's Apple~")) {
                if(!onCooldown) {
                    //Revival(Extra Health Boost)
                    if (player.getHealth() < 6) {

                        player.setHealth(player.getHealth() + 12);

                        Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                        FireworkMeta meta = firework.getFireworkMeta();
                        FireworkEffect.Builder builder = FireworkEffect.builder();
                        builder.withTrail().withFlicker().withFade(Color.GREEN, Color.WHITE, Color.YELLOW, Color.BLUE,
                                        Color.FUCHSIA, Color.PURPLE, Color.MAROON, Color.LIME, Color.ORANGE).withColor(Color.RED,Color.BLUE)
                                .with(FireworkEffect.Type.STAR);
                        meta.addEffect(builder.build());
                        meta.setPower(1);
                        firework.setFireworkMeta(meta);

                        player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "REVIVAL!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 1);

                        for(Entity entity : player.getNearbyEntities(10, 10, 10)) {
                            if(entity instanceof Monster) {
                                Vector playerVec = player.getLocation().toVector();
                                Vector entityVec = entity.getLocation().toVector();
                                entity.setVelocity(entityVec.subtract(playerVec).normalize().multiply(5));
                            }
                        }

                        event.getItem().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 20);

                        if(cooldownTime == 10.0) {
                            cooldownTime = 25.0; // <~~ Penalty for using Revival

                            //Cooldown Logic
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Angel's Apple Cooldown Ended");
                                    onCooldown = false;
                                    cooldownTime = 10.0;
                                    event.getItem().removeEnchantment(Enchantment.DAMAGE_ALL);
                                }
                            }.runTaskLater(this.plugin, (long) (cooldownTime * 20));
                        }
                        else {
                            //Cooldown Logic
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Angel's Apple Cooldown Ended");
                                    onCooldown = false;
                                }
                            }.runTaskLater(this.plugin, (long) (cooldownTime * 20));
                        }
                    } else if (Math.round(player.getHealth()) + 5 > 20) {

                            player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Healed!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                            player.setHealth(Math.round(player.getHealth()) + (20 - Math.round(player.getHealth())));

                            player.sendMessage(ChatColor.RED+ ChatColor.BOLD.toString() + "Angel's Apple is on cooldown!");
                            onCooldown = true;

                        //Cooldown Logic
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Angel's Apple Cooldown Ended");
                                onCooldown = false;
                            }
                        }.runTaskLater(this.plugin, (long) (cooldownTime * 20));
                    } else {

                            player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Healed!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                            player.setHealth(player.getHealth() + 5);
                            onCooldown = true;
                        //Cooldown Logic
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.sendMessage(ChatColor.GREEN+ ChatColor.BOLD.toString() + "Angel's Apple Cooldown Ended");
                                onCooldown = false;
                            }
                        }.runTaskLater(this.plugin, (long) (cooldownTime * 20));
                    }
                } else {
                    player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "Angel's Apple is on cooldown!");
                }

            }
        }
    }
}
