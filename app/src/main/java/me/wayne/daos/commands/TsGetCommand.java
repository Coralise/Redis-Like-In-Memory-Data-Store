package me.wayne.daos.commands;

import java.util.List;

import me.wayne.daos.Pair;
import me.wayne.daos.io.StorePrintWriter;
import me.wayne.daos.storevalues.timeseries.TimeSeries;

public class TsGetCommand extends AbstractCommand<Pair<Long, Double>> {

    public TsGetCommand() {
        super("TS.GET", 1, 1);
    }

    @Override
    protected Pair<Long, Double> processCommand(StorePrintWriter out, List<String> args) {
        String key = args.get(0);

        TimeSeries timeSeries = store.getStoreValue(key, TimeSeries.class);
        if (timeSeries == null) {
            return new Pair<>();
        }

        return timeSeries.get();
    }
    
}
