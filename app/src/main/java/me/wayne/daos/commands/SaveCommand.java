package me.wayne.daos.commands;

import java.util.UUID;

import javax.annotation.Nullable;

import java.util.List;

import me.wayne.daos.io.StorePrintWriter;
import me.wayne.persistence.PersistenceManager;

public class SaveCommand extends AbstractCommand<String> {

    public SaveCommand() {
        super("SAVE", 0);
    }

    @Override
    protected String processCommand(StorePrintWriter out, @Nullable UUID requestUuid, String inputLine, List<String> args) {
        PersistenceManager.saveStore();
        return OK_RESPONSE;
    }
    
}
