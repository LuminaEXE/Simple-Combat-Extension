package vanesa.cosntkb.cmnds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import vanesa.cosntkb.sex;

public class doubleJump implements Listener {

    public static boolean[] isDoubleJumping = new boolean[2];
    public static boolean[] isDashing = new boolean[2];
    public static boolean onCooldown = false;
    public static double cooldownTime = 2.0;

    public static boolean onCooldownDash = false;
    public static double cooldownTimeDash = 2.0;
    private final sex plugin;
    public static boolean isStick;
    public doubleJump(sex plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void checkJump(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Vector velocity = player.getVelocity();
        // Check if the player is moving "up"
        if (velocity.getY() > 0)  {
            // Default jump velocity
            double jumpVelocity = 0.42F; // Default jump velocity
            //check if player has potion effect of jump
            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.JUMP)) {
                    jumpVelocity += (float) (effect.getAmplifier() + 1) * 0.1F;
                }
            }
            // Check if player is not on ladder and if jump velocity calculated is equals to player Y velocity
            if ((player.getLocation().getBlock().getType() != Material.LADDER) && (Double.compare(velocity.getY(), jumpVelocity) == 0)) {
                isDoubleJumping[0] = true;
            }
            else {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        isDoubleJumping[0] = false;
                    }

                }.runTaskLater(this.plugin, 6);

            }
        }
    }

    @EventHandler
    public void checkSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!player.isSneaking()) {
            isDashing[0] = true;
        } else {
            isDashing[0] = false;
        }
    }

    @EventHandler
    public void stickClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        try {
            if((action == Action.PHYSICAL) ||(event.getItem().getType() == Material.AIR)) {
                return;
            }
            else if ((action == Action.PHYSICAL) || (event.getItem().getType() == Material.FISHING_ROD) &&
                    event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Stick") &&
                    event.getItem().getItemMeta().getLore().contains(ChatColor.GRAY + "Double Jump & Dash")
            ){
                // Check if item is stick && Named "~Movement Rod~"

                if (event.getItem().getItemMeta().getDisplayName().equals("~Movement Rod~")) {
                    isDoubleJumping[1] = true;
                    isDashing[1] = true;
                    isStick = true;
                } else {
                    return;
                }

                // Timeout for holdingStick Parameter
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        isDoubleJumping[1] = false;
                        isDashing[1] = false;
                        isStick = false;
                    }
                }.runTaskLater(this.plugin, 6);
            }

            if (player.getHealth() > 6 && isStick) {
                // Check if player has satisfied all conditions(Right click, not on cool down, is double jumping)
                if (isDoubleJumping[0] && isDoubleJumping[1] && !onCooldown) {
                    player.setVelocity(player.getLocation().getDirection().multiply(0.5).setY(1));
                    isDoubleJumping[0] = false;
                    isDoubleJumping[1] = false;
                    //enable cool down
                    if (!onCooldown) {
                        if (cooldownTime == 2) {
                            onCooldown = true;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    onCooldown = false;
                                    player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Double Jump Cooldown Ended");
                                }
                            }.runTaskLater(this.plugin, (long) (cooldownTime * 20));
                        } else
                            cooldownTime = 2.0;
                    }
                } else if (!isDoubleJumping[0] && !isDoubleJumping[1] && onCooldown) //cool down message
                    player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "You are on cool down!");


                // Check if player has satisfied all conditions(Right click, not on cool down, is double jumping)
                if (isDashing[0] && isDashing[1] && !onCooldownDash) {
                    player.setVelocity(player.getLocation().getDirection().multiply(3.0));
                    isDashing[0] = false;
                    isDashing[1] = false;
                    //enable cool down
                    if (!onCooldown) {
                        if (cooldownTimeDash == 2) {
                            onCooldownDash = true;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    onCooldownDash = false;
                                    player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Dash Cooldown Ended");
                                }
                            }.runTaskLater(this.plugin, (long) (cooldownTimeDash * 20));
                        } else
                            cooldownTimeDash = 2.0;
                    }
                } else if (!isDashing[0] && !isDashing[1] && onCooldownDash) //cool down message
                    player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "You are on cool down!");
            } else if (player.getHealth() < 6 && isStick) {
                player.sendMessage(
                        ChatColor.RED +
                                ChatColor.BOLD.toString() +
                                "You are on low health! movement rod is disabled." +
                                "\nYou need to be at least 6 hearts to use this item.. current health: " + Math.round(player.getHealth()
                        )
                );
            }
        } catch (NullPointerException e) {
            return;
        }
    }
}

