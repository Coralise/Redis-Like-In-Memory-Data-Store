package me.wayne.daos.commands;

import java.util.List;

import me.wayne.InMemoryStore;
import me.wayne.daos.StoreMap;
import me.wayne.daos.io.StorePrintWriter;

public class HExistsCommand extends AbstractCommand<Integer> {

    public HExistsCommand() {
        super("HEXISTS", 2, 2);
    }

    @Override
    protected Integer processCommand(StorePrintWriter out, InMemoryStore store, List<String> args) {
        String key = args.get(0);
        String field = args.get(1);
        StoreMap hashMap = store.getStoreValue(key, StoreMap.class);
        if (hashMap == null) return 0;
        return hashMap.containsKey(field) ? 1 : 0;
    }
    
}
