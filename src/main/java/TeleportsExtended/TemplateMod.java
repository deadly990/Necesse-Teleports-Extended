package TeleportsExtended;

import TeleportsExtended.Commands.TPACommand;
import TeleportsExtended.Commands.TPAccept;
import TeleportsExtended.Commands.TPReject;
import TeleportsExtended.Events.TPARequestEvent;
import TeleportsExtended.Events.TPAResponseEvent;
import TeleportsExtended.Listener.TPAListener;
import necesse.engine.GameEvents;
import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class TemplateMod {
    public void preInit() {

    }

    public void init() {
        TPAListener listener = new TPAListener();
        GameEvents.addListener(TPARequestEvent.class, listener.getRequestListener());
        GameEvents.addListener(TPAResponseEvent.class, listener.getResponseListener());
        CommandsManager.registerServerCommand(new TPACommand("tpa"));
        CommandsManager.registerServerCommand(new TPAccept("accept"));
        CommandsManager.registerServerCommand(new TPReject("reject"));
    }

    public void initResources() {

    }

    public void postInit() {

    }

    public void dispose() {

    }
}
