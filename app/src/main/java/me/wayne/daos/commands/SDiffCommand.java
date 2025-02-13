package me.wayne.daos.commands;

import java.util.UUID;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import me.wayne.daos.io.StorePrintWriter;
import me.wayne.daos.storevalues.StoreSet;
import me.wayne.daos.storevalues.StoreValue;

public class SDiffCommand extends AbstractCommand<List<String>> {

    public SDiffCommand() {
        super("SDIFF", 1);
    }

    @Override
    protected List<String> processCommand(StorePrintWriter out, @Nullable UUID requestUuid, String inputLine, List<String> args) {
        List<String> keys = args;
        List<StoreSet> hashSets = new ArrayList<>();
        for (String key : keys) {
            StoreValue storeValue = store.getStoreValue(key);
            StoreSet hashSet = storeValue == null ? new StoreSet() : storeValue.getValue(StoreSet.class);
            hashSets.add(hashSet);
        }
        HashSet<String> difference = new HashSet<>(hashSets.get(0));
        for (int i = 1;i < hashSets.size();i++) difference.removeAll(hashSets.get(i));
        return new ArrayList<>(difference);
    }
    
}
