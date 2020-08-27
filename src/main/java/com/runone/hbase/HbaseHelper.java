package com.runone.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HbaseHelper {

    private static final String ZKconnect = "192.168.10.102:2181,192.168.10.103:2181,192.168.10.104:2181";

    private Configuration conf;

    private String tableName = "trafficinfo";

    private String familyName = "location";

    private HTable htable;

    public HbaseHelper() {
        this.conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", ZKconnect);
        try {
            this.htable = new HTable(conf, Bytes.toBytes(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HTable getHtable() {
        return htable;
    }

}
