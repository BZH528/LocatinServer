package com.runone.listener;

import com.rabbitmq.client.Channel;
import com.runone.hbase.HbaseHelper;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 监听mq里面的消息内容，然后写进hbase里面
 */
@Component
public class LocationMessageListener {

    @Autowired
    private HbaseHelper hbaseHelper;

    private List<Put> dataList;

    private final String[] columns = {"logitude", "latitude", "locatetime"};

    private Logger logger;

    @Value("${hbase.dataput.max_count_per_submit}")
    private int maxCount;

    public LocationMessageListener() {
        dataList = new ArrayList<Put>();
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @RabbitListener(queues = "topic.location.all")
    public void getMessage(Message message, Channel channel) {
        String msg = new String(message.getBody());
        String[] split = msg.split(",");
        try {
            ArrayList<String> values = new ArrayList<String>();
            values.add(split[6]);
            values.add(split[7]);
            values.add(split[5]);
            this.putDataToHbase(split[0], "location", columns, values);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("there is something wrong in put data to hbase");
        }
    }

    public void putDataToHbase(String rowkey, String family, String[] columns, List<String> values) throws IOException {
        Put put = new Put(Bytes.toBytes(rowkey));
        for (int i = 0; i < columns.length; i++) {
            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(columns[i]), Bytes.toBytes(values.get(i)));
        }
        if (this.dataList == null) {
            this.dataList = new ArrayList<Put>();
        }
        this.dataList.add(put);
        if (this.dataList.size() >= this.maxCount) {
            HTable htable = this.hbaseHelper.getHtable();
            htable.put(this.dataList);
            this.dataList = new ArrayList<Put>();
            logger.info("========================成功发送到hbase======================");
        }
    }
}
