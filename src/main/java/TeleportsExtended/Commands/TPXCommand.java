package TeleportsExtended.Commands;

import TeleportsExtended.Data.TeleportPos;
import TeleportsExtended.Static.Methods;
import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.IntParameterHandler;
import necesse.engine.commands.parameterHandlers.PresetStringParameterHandler;
import necesse.engine.commands.parameterHandlers.ServerClientParameterHandler;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

public class TPXCommand extends ModularChatCommand {
    public TPXCommand(String name) {
        super(name, "Request to teleport to specific coordinates", PermissionLevel.ADMIN, false , new CmdParameter("player", new ServerClientParameterHandler(true, true), true)
                                                                                                            , new CmdParameter("coordType", new PresetStringParameterHandler(true, "abs", "rel", "rel"), true)
                                                                                                            , new CmdParameter("coordsX", new IntParameterHandler())
                                                                                                            , new CmdParameter("coordsY", new IntParameterHandler())
                                                                                                            , new CmdParameter("islandCoordsX", new IntParameterHandler(Integer.MIN_VALUE), true)
                                                                                                            , new CmdParameter("islandCoordsY", new IntParameterHandler(Integer.MIN_VALUE), true)
                                                                                                            , new CmdParameter("dimension", new IntParameterHandler(Integer.MIN_VALUE), true));
    }

    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog logs) {
        ServerClient target = (ServerClient)args[0];
        String coordType = (String) args[1];
        boolean relative = coordType.equals("rel");
        int coordsX = (int) args[2];
        int coordsY = (int) args[3];
        int islandCoordsX = (int) args[4];
        int islandCoordsY = (int) args[5];
        int dimension = (int) args[6];
        islandCoordsX = islandCoordsX > Integer.MIN_VALUE ? islandCoordsX : target.getIslandX();
        islandCoordsY = islandCoordsY > Integer.MIN_VALUE ? islandCoordsY : target.getIslandX();
        dimension = dimension > Integer.MIN_VALUE ? dimension : target.getDimension();
        if (relative) {
            coordsX += target.playerMob.getX();
            coordsY += target.playerMob.getY();
        }
        TeleportPos to = new TeleportPos(target, "", islandCoordsX, islandCoordsY, dimension, coordsX, coordsY);
        Methods.ExecuteTeleport(server, target, to);
    }

    public boolean shouldBeListed() {
        return true;
    }
}
