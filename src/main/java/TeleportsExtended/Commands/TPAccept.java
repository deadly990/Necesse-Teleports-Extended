package TeleportsExtended.Commands;

import TeleportsExtended.Events.TPARequestEvent;
import TeleportsExtended.Events.TPAResponseEvent;
import necesse.engine.GameEvents;
import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.ServerClientParameterHandler;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

import java.util.concurrent.atomic.AtomicBoolean;

public class TPAccept extends ModularChatCommand {
    public TPAccept(String name) {
        super(name, "Respond to teleport to another player", PermissionLevel.USER, false);
    }

    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog logs) {
        AtomicBoolean accepted = new AtomicBoolean(false);
            GameEvents.triggerEvent(new TPAResponseEvent(serverClient, true), (TPAResponseEvent e) -> {
                accepted.set(true);
            });
        if(!accepted.get()) {
            logs.addClient("No pending teleport requests" , serverClient);
        }
    }
    public boolean shouldBeListed() {
        return false;
    }
}
