package TeleportsExtended.Events;

import TeleportsExtended.Data.TeleportPos;
import TeleportsExtended.Static.Methods;
import necesse.engine.commands.CommandLog;
import necesse.engine.events.PreventableGameEvent;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

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
        Methods.ExecuteTeleport(server, source, to);
    }

    public void expire() {
        logs.addClient("TPA Request to " + target.getName() + " expired", source);
        logs.addClient("TPA Request from " + source.getName() + " expired", target);
    }

    public void reject() {
        logs.addClient("TPA Request to " + target.getName() + " rejected", source);
        logs.addClient("TPA Request from " + source.getName() + " rejected", target);
    }

}
