package vanesa.cosntkb;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import vanesa.cosntkb.cmnds.hitEvent;
import vanesa.cosntkb.cmnds.*;

public final class sex extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("!~CONST KB INITIALIZED~!");

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new hitEvent(), this);
        pm.registerEvents(new doubleJump(this), this);
        pm.registerEvents(new healing(this),this);


        this.getCommand("velocity").setExecutor(new velocity());
        this.getCommand("setHealth").setExecutor(new healingCmds());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
