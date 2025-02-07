package me.wayne.daos.commands;

import java.util.List;
import java.util.Map;

import me.wayne.InMemoryStore;

public class HExistsCommand extends AbstractCommand<Integer> {

    public HExistsCommand() {
        super("HEXISTS", 2, 2);
    }

    @Override
    protected Integer processCommand(Thread thread, InMemoryStore store, List<String> args) {
        String key = args.get(0);
        String field = args.get(1);
        if (!store.getStore().containsKey(key)) return 0;
        Map<String, String> hashMap = getMap(store, key);
        return hashMap.containsKey(field) ? 1 : 0;
    }
    
}
