package me.machinemaker.regionsplus.misc;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static java.util.Objects.isNull;

public class Sender {

    private Player player;
    private CommandSender console;

    public Sender(Player player) {
        this.player = player;
        this.console = player;
    }

    public Sender(CommandSender console) {
        this.console = console;
        this.player = null;
    }

    public CommandSender getSender() {
        return console;
    }

    public boolean isPlayer() {
        return !isNull(this.player);
    }

    public void sendMessage(String msg) {
        this.console.sendMessage(msg);
    }

    public void sendMessage(BaseComponent...components) {
        this.console.sendMessage(components);
    }

    /**
     * Sends action bar message to players or a normal message to console
     * @param msg Message string to send (NO PREFIX)
     */
    public void sendActionMsg(String msg) {
        if (!isNull(this.player)) this.player.sendActionBar(msg);
        else this.console.sendMessage(Lang.PREFIX.toString() + msg);
    }
}
