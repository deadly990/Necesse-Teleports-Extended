package TeleportsExtended.Events;

import necesse.engine.commands.CommandLog;
import necesse.engine.commands.serverCommands.TeleportServerCommand;
import necesse.engine.events.PreventableGameEvent;
import necesse.engine.network.packet.PacketPlayerMovement;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

import java.awt.*;

public class TPARequestEvent extends PreventableGameEvent {
    public final ServerClient target;
    public final ServerClient source;
    private final Server server;
    private final CommandLog logs;

    public TPARequestEvent(ServerClient source, ServerClient target, Server server, CommandLog logs) {
        this.source = source;
        this.target = target;
        this.server = server;
        this.logs = logs;
    }
    private TeleportPos clientPos(ServerClient client) {
        return client == null ? null : new TeleportPos(client, client.getName(), client.getIslandX(), client.getIslandY(), client.getDimension(), (float)client.playerMob.getX(), (float)client.playerMob.getY());
    }
    public void execute() {
        TeleportPos to = clientPos(target);
        if (source.isSamePlace(to.islandX, to.islandY, to.dimension)) {
            source.playerMob.setPos(to.levelX, to.levelY, true);
            server.network.sendToClientsAt(new PacketPlayerMovement(source, true), (ServerClient) source);
        } else {
            Point point = new Point((int) to.levelX, (int) to.levelY);
            source.changeIsland(to.islandX, to.islandY, to.dimension, (level) -> {
                return point;
            }, true);
        }
    }

    public void expire() {
        logs.addClient("TPA Request to " + target.getName() + " expired", source);
        logs.addClient("TPA Request from " + source.getName() + " expired", target);
    }

    public void reject() {
        logs.addClient("TPA Request to " + target.getName() + " rejected", source);
        logs.addClient("TPA Request from " + source.getName() + " rejected", target);
    }

    private static class TeleportPos {
        public final ServerClient client;
        public final String name;
        public final int islandX;
        public final int islandY;
        public final int dimension;
        public final float levelX;
        public final float levelY;

        private TeleportPos(ServerClient client, String name, int islandX, int islandY, int dimension, float levelX, float levelY) {
            this.client = client;
            this.name = name;
            this.islandX = islandX;
            this.islandY = islandY;
            this.dimension = dimension;
            this.levelX = levelX;
            this.levelY = levelY;
        }
    }
}
