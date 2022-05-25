package de.sesosas.simpletablist;

import de.sesosas.simpletablist.classes.TabHeadFoot;
import de.sesosas.simpletablist.classes.TabName;
import de.sesosas.simpletablist.message.MessageHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.log.LogPublishEvent;
import net.luckperms.api.node.types.PermissionNode;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.MultiLineChart;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public final class SimpleTabList extends JavaPlugin implements Listener {

    public FileConfiguration config = getConfig();

    private static SimpleTabList plugin;

    public static SimpleTabList getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        config.addDefault("PlaceholderInfo", "Placeholder using PlaceholderAPI [Example: \"Welcome {player_name} on this Server!\"]");
        config.addDefault("Tab.Header", "Welcome on my Server!");
        config.addDefault("Tab.Footer", "Enjoy your stay!");
        config.addDefault("Event.Use", false);
        config.addDefault("Event.JoinMessage", "The Player {player_name} joined the Server!");
        config.addDefault("Event.QuitMessage", "The Player {player_name} left the Server!");
        config.addDefault("Chat.Use", false);
        config.addDefault("Chat.Prefix", "§f[§cSTL§f]");
        config.addDefault("Chat.Separator", " >> ");
        config.addDefault("Chat.Colors", true);
        config.addDefault("Homes.Use", true);
        config.addDefault("Homes.Amount", 5);
        config.addDefault("Plugin.ActionbarMessage", false);
        config.addDefault("Plugin.NoticeMe", "You need LuckPerms to get this Plugin to work!");
        config.addDefault("bstats.Use", true);
        config.options().copyDefaults(true);
        saveConfig();

        /*
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms luckPerms = provider.getProvider();
            EventBus eventBus = luckPerms.getEventBus();

            eventBus.subscribe(plugin, LogPublishEvent.class, e -> {
                TabName.Update();
                TabHeadFoot.Update();
            });
        }
         */

        if(config.getBoolean("bstats.Use")){
            int id = 15221;
            Metrics metrics = new Metrics(this, id);
            metrics.addCustomChart(new SingleLineChart("servers", () -> 1));
            metrics.addCustomChart(new SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));
            metrics.addCustomChart(new SingleLineChart("banned", () -> Bukkit.getBannedPlayers().size()));
        }

        new UpdateChecker(this, 101989).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available.");
            }
        });

        new BukkitRunnable() {

            @Override
            public void run() {
                TabHeadFoot.Update();
                TabName.Update();
            }
        }.runTaskTimer(this, 0, 20L);

        getServer().getPluginManager().registerEvents(new IEventHandler(), this);
        getCommand("stl").setExecutor(new CommandHandler());
        System.out.println("Simple TabList has started!");
    }



}