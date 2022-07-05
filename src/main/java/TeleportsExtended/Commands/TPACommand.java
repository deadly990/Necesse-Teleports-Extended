package TeleportsExtended.Commands;

import TeleportsExtended.Events.TPARequestEvent;
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

public class TPACommand extends ModularChatCommand {
    public TPACommand(String name) {
        super(name, "Request to teleport to another player", PermissionLevel.USER, false, new CmdParameter("player", new ServerClientParameterHandler()));
    }

    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog logs) {
        ServerClient target = (ServerClient)args[0];
        if (target != null && target != serverClient) {
            String clientName = serverClient == null ? "Server" : serverClient.getName();
            logs.add("Teleport To " + target.getName());
            logs.addClient("Teleport Request From " + clientName, target);
            logs.addClient("Use /accept or /reject " + clientName, target);
            AtomicBoolean accepted = new AtomicBoolean(false);
            GameEvents.triggerEvent(new TPARequestEvent(serverClient, target, server, logs), (TPARequestEvent e) -> {
                accepted.set(true);
            });
            if(!accepted.get()) {
                logs.addClient(clientName + " already has a teleport pending", serverClient);
            }
        } else {
            logs.add("Cannot whisper self");
        }
    }
    public boolean shouldBeListed() {
        return true;
    }
}
