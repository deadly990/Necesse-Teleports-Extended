package TeleportsExtended.Static;

import TeleportsExtended.Data.TeleportPos;
import necesse.engine.network.packet.PacketPlayerMovement;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

import java.awt.*;

public class Methods {
    public static void ExecuteTeleport(Server server, ServerClient target, TeleportPos to) {
        if (target.isSamePlace(to.islandX, to.islandY, to.dimension)) {
            target.playerMob.setPos(to.levelX, to.levelY, true);
            server.network.sendToClientsAt(new PacketPlayerMovement(target, true), target);
        } else {
            Point point = new Point((int) to.levelX, (int) to.levelY);
            target.changeIsland(to.islandX, to.islandY, to.dimension, (level) -> {
                return point;
            }, true);
        }
    }
}
