package TeleportsExtended.Data;

import necesse.engine.network.server.ServerClient;

public class TeleportPos {
    public final ServerClient client;
    public final String name;
    public final int islandX;
    public final int islandY;
    public final int dimension;
    public final float levelX;
    public final float levelY;

    public TeleportPos(ServerClient client, String name, int islandX, int islandY, int dimension, float levelX, float levelY) {
        this.client = client;
        this.name = name;
        this.islandX = islandX;
        this.islandY = islandY;
        this.dimension = dimension;
        this.levelX = levelX;
        this.levelY = levelY;
    }
}
