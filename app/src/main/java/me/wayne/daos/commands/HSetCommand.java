package me.wayne.daos.commands;

import java.util.List;

import me.wayne.InMemoryStore;
import me.wayne.daos.StoreMap;
import me.wayne.daos.io.StorePrintWriter;

public class HSetCommand extends AbstractCommand<Integer> {

    public HSetCommand() {
        super("HSET", 3);
    }

    @Override
    protected Integer processCommand(StorePrintWriter out, InMemoryStore store, List<String> args) {
        String key = args.get(0);
        List<String> fieldsAndValues = args.subList(1, args.size());
        StoreMap hashMap = new StoreMap();
        int added = 0;
        for (int i = 1; i < fieldsAndValues.size(); i += 2) {
            hashMap.put(fieldsAndValues.get(i-1), fieldsAndValues.get(i));
            added++;
        }
        store.setStoreValue(key, hashMap);
        return added;
    }
    
}
