package me.wayne.daos.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.wayne.InMemoryStore;
import me.wayne.daos.StoreMap;
import me.wayne.daos.io.StorePrintWriter;

public class HGetAllCommand extends AbstractCommand<List<String>> {

    public HGetAllCommand() {
        super("HGETALL", 1, 1);
    }

    @Override
    protected List<String> processCommand(StorePrintWriter out, InMemoryStore store, List<String> args) {
        String key = args.get(0);
        StoreMap hashMap = store.getStoreValue(key, StoreMap.class);
        if (hashMap == null) return new ArrayList<>();
        List<String> fieldsAndValues = new ArrayList<>();
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            fieldsAndValues.add(entry.getKey());
            fieldsAndValues.add(entry.getValue());
        }
        return fieldsAndValues;
    }
    
}
