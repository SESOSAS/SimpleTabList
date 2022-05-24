package de.sesosas.simpletablist.classes;

import de.sesosas.simpletablist.SimpleTabList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TabHeadFoot {
    public static void Update(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            //TabList Header
            String HeaderText = StringFormater.Get(SimpleTabList.getPlugin().config.getString("Tab.Header"), player);
            player.setPlayerListHeader(HeaderText);

            //TabList Footer
            String FooterText = StringFormater.Get(SimpleTabList.getPlugin().config.getString("Tab.Footer"), player);
            player.setPlayerListFooter(FooterText);
        }
    }
}
