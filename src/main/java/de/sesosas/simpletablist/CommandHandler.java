package de.sesosas.simpletablist;

import de.sesosas.simpletablist.classes.CustomConfig;
import de.sesosas.simpletablist.commands.ChatCommands;
import de.sesosas.simpletablist.commands.HomeCommand;
import de.sesosas.simpletablist.commands.ReloadCommand;
import de.sesosas.simpletablist.permissions.PermissionsHandler;
import de.sesosas.simpletablist.message.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{
            if(sender instanceof Player) {
                Player player = (Player) sender;
                List<String> commands = new ArrayList<>();
                commands.add("chat");
                commands.add("homes");
                commands.add("reload");

                if (args[0].equalsIgnoreCase(commands.get(0))) {
                    ChatCommands.Do(player, args);
                } else if (args[0].equalsIgnoreCase(commands.get(1))) {
                    HomeCommand.Do(player, args);
                } else if(args[0].equalsIgnoreCase(commands.get(2))) {
                    ReloadCommand.Do(player, args);
                } else {
                    MessageHandler.Send(player, ChatColor.DARK_RED + "This Command doesn't exists!");
                }
            }
            else {
                sender.sendMessage("You are not be able to use this command!");
            }
            return true;
        }
        catch (Exception e){
            if(sender instanceof Player) {
                Player player = (Player) sender;
                MessageHandler.Send(player, ChatColor.AQUA + "Commands:"
                        + "\n - /stl chat <args>"
                        + "\n - /stl reload");
            }
            else{
                sender.sendMessage("You are not be able to use this command!");
            }
            System.out.println(e);
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("chat");

            if(PermissionsHandler.hasPermission(player, "stl.home")){
                if(SimpleTabList.getPlugin().config.getBoolean("Homes.Use")){
                    arguments.add("homes");
                }
            }
            if (PermissionsHandler.hasPermission(player, "stl.reload")) {
                arguments.add("reload");
            }
            return arguments;
        }
        else if(args.length == 2){
            List<String> arguments = new ArrayList<>();
            CustomConfig.setup(player);
            FileConfiguration con = CustomConfig.get();
            if(args[0].equalsIgnoreCase("homes")){
                if(SimpleTabList.getPlugin().config.getBoolean("Homes.Use")){
                    if(PermissionsHandler.hasPermission(player, "stl.home.add")){
                        arguments.add("add");
                        arguments.add("remove");
                        if(PermissionsHandler.hasPermission(player, "stl.home")){
                            for(int i = 1; i <= SimpleTabList.getPlugin().config.getInt("Homes.Amount"); i++){
                                if(con.get("Homes."+i) != null){
                                    if(!con.getString("Homes."+i+".Name").equalsIgnoreCase("Deleted")){
                                        arguments.add(con.getString("Homes."+i+".Name"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else{
                if(PermissionsHandler.hasPermission(player, "stl.chat.staff")){
                    arguments.add("staff");
                    arguments.add("mute");
                    arguments.add("unmute");
                }

                if(PermissionsHandler.hasPermission(player, "stl.chat.clear")){
                    arguments.add("clear");
                }
                return arguments;
            }
            return arguments;
        }
        else if(args.length == 3){
            if(args[1].equalsIgnoreCase("staff")){
                if(PermissionsHandler.hasPermission(player, "stl.chat.staff")){
                    List<String> arguments = new ArrayList<>();
                    arguments.add("enable");
                    arguments.add("disable");
                    return arguments;
                }
                else{
                    return null;
                }
            }
            else if(args[1].equalsIgnoreCase("remove")){
                if(SimpleTabList.getPlugin().config.getBoolean("Homes.Use")){
                    CustomConfig.setup(player);
                    FileConfiguration con = CustomConfig.get();
                    List<String> arguments = new ArrayList<>();
                    for(int i = 1; i <= SimpleTabList.getPlugin().config.getInt("Homes.Amount"); i++){
                        if(con.get("Homes."+i) != null){
                            if(!con.getString("Homes."+i+".Name").equalsIgnoreCase("Deleted")){
                                arguments.add(con.getString("Homes."+i+".Name"));
                            }
                        }
                    }
                    return arguments;
                }
                CustomConfig.setup(player);
                FileConfiguration con = CustomConfig.get();
                List<String> arguments = new ArrayList<>();
                for(int i = 1; i <= SimpleTabList.getPlugin().config.getInt("Homes.Amount"); i++){
                    if(con.get("Homes."+i) != null){
                        if(!con.getString("Homes."+i+".Name").equalsIgnoreCase("Deleted")){
                            arguments.add(con.getString("Homes."+i+".Name"));
                        }
                    }
                }
                return arguments;
            }
            else{
                if(PermissionsHandler.hasPermission(player, "stl.chat.clear.other")){
                    List<String> arguments = new ArrayList<>();
                    for(Player player1 : Bukkit.getOnlinePlayers()){
                        arguments.add(player1.getPlayer().getName());
                    }
                    return arguments;
                }
                else{
                    return null;
                }
            }
        }
        else{
            return null;
        }
    }
}
