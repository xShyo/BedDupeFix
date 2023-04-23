package ijuanitoh.com;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, (Plugin)this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBedClick(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();
        if (block == null || item == null || !item.getType().name().contains("BED"))
            return;
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(e.getPlayer());
        Location loc = new Location((Extent)new BukkitWorld(block.getWorld()), block.getX(), block.getY(), block.getZ());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        if (!query.testBuild(loc, localPlayer, new StateFlag[] { Flags.BUILD }))
            e.setCancelled(true);
    }
}
