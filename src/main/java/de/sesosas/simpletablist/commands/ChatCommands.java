package de.sesosas.simpletablist.commands;

import de.sesosas.simpletablist.classes.CustomConfig;
import de.sesosas.simpletablist.classes.Nametag;
import de.sesosas.simpletablist.message.MessageHandler;
import de.sesosas.simpletablist.permissions.PermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ChatCommands{

    public static void Do(Player player, String[] args){
        if(args.length >= 2){
            if(args[1].equalsIgnoreCase("clear")){
                ClearChat(player, args);
            }
            else if(args[1].equalsIgnoreCase("staff")){
                StaffChat(player, args);
            }
            else if(args[1].equalsIgnoreCase("mute")){
                MutePlayer(player, args);
            }
            else if(args[1].equalsIgnoreCase("unmute")){
                UnmutePlayer(player, args);
            }
            else{
                MessageHandler.Send(player, ChatColor.DARK_RED + "This command doesn't exist!");
            }
        }
        else{
            MessageHandler.Send(player, ChatColor.DARK_RED + "You need to provide <clear/staff>!");
        }
    }

    private static void MutePlayer(Player player, String[] args){
        if(args.length == 3){
            if(PermissionsHandler.hasPermission(player, "stl.chat.staff")){
                Player target = Bukkit.getPlayer(args[2]);
                if(target != null){
                    CustomConfig.setup(target);
                    FileConfiguration con = CustomConfig.get();
                    if(con.getBoolean("Chat.Muted")){
                        MessageHandler.Send(player, ChatColor.DARK_RED + "The Player " +target.getDisplayName()+ ChatColor.DARK_RED +" is already muted!");
                    }
                    else{
                        con.set("Chat.Muted", true);
                        CustomConfig.save();
                        CustomConfig.reload();
                        MessageHandler.Send(target, ChatColor.YELLOW + "You got muted!");
                        MessageHandler.Send(player, target.getDisplayName() + ChatColor.YELLOW + " got muted now!");
                    }
                }
                else{
                    MessageHandler.Send(player, ChatColor.DARK_RED + "The Player needs to be online!");
                }
            }
        }
    }

    private static void UnmutePlayer(Player player, String[] args){
        if(args.length == 3){
            if(PermissionsHandler.hasPermission(player, "stl.chat.staff")){
                Player target = Bukkit.getPlayer(args[2]);
                if(target != null){
                    CustomConfig.setup(target);
                    FileConfiguration con = CustomConfig.get();
                    if(con.getBoolean("Chat.Muted")){
                        con.set("Chat.Muted", false);
                        CustomConfig.save();
                        CustomConfig.reload();
                        MessageHandler.Send(target, ChatColor.YELLOW + "You got unmuted!");
                        MessageHandler.Send(player, target.getDisplayName() + ChatColor.YELLOW + " got unmuted now!");
                    }
                    else{
                        MessageHandler.Send(player, ChatColor.DARK_RED + "The Player " +target.getDisplayName()+ ChatColor.DARK_RED +" is not muted!");
                    }
                }
            }
        }
    }

    private static void ClearChat(Player player, String[] args) {

        if(args.length == 3){
            if(PermissionsHandler.hasPermission(player, "stl.chat.clear.other")){
                try{
                    Player target = Bukkit.getPlayer(args[2]);
                    for(int i=0; i<200; i++)
                    {
                        target.sendMessage("");
                    }
                    MessageHandler.Send(player, ChatColor.AQUA + "Cleared the chat of "+ target.getDisplayName());
                }
                catch (Exception e){
                    MessageHandler.Send(player, ChatColor.DARK_RED + "Couldn't clear the chat of "+args[2]);
                }
            }
            else{
                MessageHandler.Send(player, ChatColor.DARK_RED + "You are not allowed to use this Command!");
            }
        }
        else{
            if(PermissionsHandler.hasPermission(player, "stl.chat.clear")){
                for(int i=0; i<200; i++)
                {
                    Bukkit.broadcastMessage("");
                }
            }
            else{
                MessageHandler.Send(player, ChatColor.DARK_RED + "You are not allowed to use this Command!");
            }
        }
    }


    private static void StaffChat(Player player, String[] args) {
        if(args.length == 3){
            if(PermissionsHandler.hasPermission(player, "stl.chat.staff")){
                CustomConfig.setup(player);
                FileConfiguration con = CustomConfig.get();
                if(args[2].equalsIgnoreCase("enable")){
                    if(con.getBoolean("Chat.Staff")){
                        MessageHandler.Send(player, ChatColor.DARK_RED + "Staff chat already enabled!");
                    }
                    else{
                        con.set("Chat.Staff", true);
                        CustomConfig.save();
                        CustomConfig.reload();
                        MessageHandler.Send(player, ChatColor.YELLOW + "Staff chat is now enabled!");
                    }
                }
                else if(args[2].equalsIgnoreCase("disable")){
                    if(con.getBoolean("Chat.Staff")){
                        con.set("Chat.Staff", false);
                        CustomConfig.save();
                        CustomConfig.reload();
                        MessageHandler.Send(player, ChatColor.YELLOW + "Staff chat is now disabled!");
                    }
                    else{
                        MessageHandler.Send(player, ChatColor.DARK_RED + "Staff chat already disabled!");
                    }
                }
                else{
                    MessageHandler.Send(player, ChatColor.DARK_RED + "This command doesn't exist!");
                }
            }
            else{
                MessageHandler.Send(player, ChatColor.DARK_RED + "You are not allowed to use this Command!");
            }
        }
        else{
            MessageHandler.Send(player, ChatColor.DARK_RED + "You need to provide <enable/disable>!");
        }
    }
}


