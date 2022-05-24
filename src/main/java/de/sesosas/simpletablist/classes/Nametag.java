package de.sesosas.simpletablist.classes;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Nametag {

    public static String luckpermsName(Player player){
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
                CachedMetaData metaData = api.getPlayerAdapter(Player.class).getMetaData(player);

                String name = player.getDisplayName();
                String prefix = "";
                String suffix = "";

                if(metaData.getPrefix() != null && !metaData.getPrefix().equalsIgnoreCase("") && !metaData.getPrefix().equalsIgnoreCase("null")){
                    prefix = StringFormater.Get(metaData.getPrefix());
                }
                if(metaData.getSuffix() != null && !metaData.getSuffix().equalsIgnoreCase("") && !metaData.getPrefix().equalsIgnoreCase("null")){
                    suffix = StringFormater.Get(metaData.getSuffix());
                }

                return prefix + name + suffix;
        }
        else{
            System.out.println("Didn't found LuckPerms which is necessary for this Plugin!");
            return player.getDisplayName();
        }
    }

}
