package dev.mrbutters.me.slimeindicator.commands;

import dev.mrbutters.me.slimeindicator.SlimeIndicator;
import dev.mrbutters.me.slimeindicator.utils.HexFormat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlimeChunkCheck implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(SlimeIndicator.getInstance().getConfig().getBoolean("requirePerms")) {
            if (!(sender.hasPermission("slimeindicator.command"))) {
                sender.sendMessage(ChatColor.RED + "Invalid Permission!");
                return false;
            }
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Chunk chunk = player.getLocation().getChunk();

            String message;

            if (chunk.isSlimeChunk()) {
                message = HexFormat.format(SlimeIndicator.getInstance().getConfig().getString("isSlimeChunkActionBar"));

                int minX = chunk.getX()*16;
                int minZ = chunk.getZ()*16;
                int minY = player.getLocation().getBlockY();
                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(100, 255, 0), 1);

                for (int y=0; y<3; y++) {
                    for (int x = 0; x < 17; x++) {
                        player.spawnParticle(Particle.REDSTONE, minX + x, minY+y, minZ, 20, dustOptions);
                    }
                    for (int x = 0; x < 17; x++) {
                        player.spawnParticle(Particle.REDSTONE, minX + x, minY+y, minZ + 16, 20, dustOptions);
                    }
                    for (int z = 0; z < 17; z++) {
                        player.spawnParticle(Particle.REDSTONE, minX, minY+y, minZ + z, 20, dustOptions);
                    }
                    for (int z = 0; z < 17; z++) {
                        player.spawnParticle(Particle.REDSTONE, minX + 16, minY+y, minZ + z, 20, dustOptions);
                    }
                }


                if (SlimeIndicator.getInstance().getConfig().getBoolean("doSound")) {
                    player.playSound(player.getLocation(),
                            Sound.valueOf(SlimeIndicator.getInstance().getConfig().getString("soundSettings.sound")),
                            (float) SlimeIndicator.getInstance().getConfig().getDouble("soundSettings.vol"),
                            (float) SlimeIndicator.getInstance().getConfig().getDouble("soundSettings.pitch"));
                }
            } else {
                message = HexFormat.format(SlimeIndicator.getInstance().getConfig().getString("isNotSlimeChunkActionBar"));
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

        } else {
            sender.sendMessage(ChatColor.RED + "Only a player can run this command!");
        }

        return false;
    }
}
