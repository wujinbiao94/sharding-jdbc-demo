package com.example.demo.config;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;
public class DemoDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        int dataBaseNum = dataSourceConfig.getDataBaseNum(), tableNum=dataSourceConfig.getTableNum();

        Long tmp = Long.parseLong(preciseShardingValue.getValue().toString())%(dataBaseNum*tableNum);
        for (String each : collection) {
            if (each.endsWith(tmp / tableNum+"")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }


}
