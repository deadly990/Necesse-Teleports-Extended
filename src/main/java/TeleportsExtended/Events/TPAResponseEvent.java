package TeleportsExtended.Events;

import necesse.engine.events.PreventableGameEvent;
import necesse.engine.network.server.ServerClient;

public class TPAResponseEvent extends PreventableGameEvent {
    public final ServerClient teleportTarget;
    public final boolean accepted;


    public TPAResponseEvent(ServerClient teleportTarget, boolean accepted) {
        this.teleportTarget = teleportTarget;
        this.accepted = accepted;
    }
}
