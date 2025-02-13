package me.wayne.daos.commands;

import java.util.List;

import me.wayne.daos.io.StorePrintWriter;
import me.wayne.daos.storevalues.StoreList;
import me.wayne.daos.storevalues.StoreValue;

public class LPushCommand extends AbstractCommand<Integer> {

    public LPushCommand() {
        super("LPUSH", 2);
    }

    @Override
    protected Integer processCommand(StorePrintWriter out, List<String> args) {
        String key = args.get(0);
        List<String> values = args.subList(1, args.size());
        StoreValue storeValue = store.getStoreValue(key);
        StoreList list = storeValue == null ? new StoreList() : storeValue.getValue(StoreList.class);
        for (String value : values) list.addFirst(value);
        store.setStoreValue(key, list);
        return list.size();
    }
    
}
