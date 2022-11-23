package vanesa.cosntkb.cmnds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static vanesa.cosntkb.cmnds.velocity.showHitMessages;
import static vanesa.cosntkb.cmnds.velocity.velocityAmplifier;


public class hitEvent implements Listener {
    @EventHandler
    public void changeHitKnockback(EntityDamageByEntityEvent event) {
        //if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Entity targetEntity = event.getEntity();
            Player player = (Player) event.getDamager();

            targetEntity.setVelocity(player.getLocation().getDirection().multiply(velocityAmplifier));
            if(showHitMessages) {
                player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + player.getDisplayName() + " Hit " + targetEntity.getType());
            }
        //}
    }
}
