package me.wayne.daos.commands;

import java.util.UUID;

import javax.annotation.Nullable;

import java.util.List;

import me.wayne.daos.io.StorePrintWriter;
import me.wayne.daos.storevalues.ScoreMember;
import me.wayne.daos.storevalues.StoreSortedSet;

public class ZRankCommand extends AbstractCommand<Object> {

    public ZRankCommand() {
        super("ZRANK", 2, 3);
    }

    @Override
    protected Object processCommand(StorePrintWriter out, @Nullable UUID requestUuid, String inputLine, List<String> args) {
        String key = args.get(0);
        String member = args.get(1);
        boolean withScore = args.size() == 3 && args.get(2).equals("WITHSCORE");
        StoreSortedSet treeSet = store.getStoreValue(key, StoreSortedSet.class, new StoreSortedSet());
        ScoreMember scoreMember = treeSet.floor(new ScoreMember(member));
        if (scoreMember == null || !scoreMember.getMember().equals(member)) return null;
        int rank = treeSet.headSet(scoreMember).size();
        return withScore ? List.of(rank, scoreMember.getScore()) : rank;
    }
    
}
