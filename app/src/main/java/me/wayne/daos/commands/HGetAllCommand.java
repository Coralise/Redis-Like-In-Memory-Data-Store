package me.wayne.daos.commands;

import java.util.UUID;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import me.wayne.daos.io.StorePrintWriter;
import me.wayne.daos.storevalues.StoreMap;

public class HGetAllCommand extends AbstractCommand<List<String>> {

    public HGetAllCommand() {
        super("HGETALL", 1, 1);
    }

    @Override
    protected List<String> processCommand(StorePrintWriter out, @Nullable UUID requestUuid, String inputLine, List<String> args) {
        String key = args.get(0);
        StoreMap hashMap = store.getStoreValue(key, StoreMap.class);
        if (hashMap == null) return new ArrayList<>();
        return hashMap.getFieldsAndValues();
    }
    
}
