package com.example.balancelogservice.Service;

import com.example.balancelogservice.Dto.TransactionResponse;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@Configurable
@Service
public class ReadLogService {

    @Value("${Dbhostname}")
    private String hostname;

    @Value("${Dbport}")
    private int port;

    @Value("${Dbschema}")
    private String schema;

    @Value("${Dbusername}")
    private String username;

    @Value("${Dbpassword}")
    private String password;

    @Value("${Dbtablename}")
    private String tableName;
    private long tableId;

    @Autowired
    @Qualifier("transactionKafkaTemplate")
    KafkaTemplate<String, TransactionResponse> transactionKafkaTemplate;


    public void ReadLog() {
        BinaryLogClient client = new BinaryLogClient(hostname, port, schema, username, password);
        registerToBinarylog(client);
        try {
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void registerToBinarylog(BinaryLogClient client){
        client.registerEventListener(event -> {

            EventData data = event.getData();
            if (data instanceof TableMapEventData) {
                TableMapEventData tableMapEventData = (TableMapEventData) data;
                if (tableMapEventData.getTable().equals(tableName)){
                    tableId = tableMapEventData.getTableId();
                }
            }
            if (data instanceof  UpdateRowsEventData){
                UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;
                if (updateRowsEventData.getTableId() == tableId){
                    for (Map.Entry<Serializable[], Serializable[]> row : updateRowsEventData.getRows()) {
                        Serializable[] before = row.getKey();
                        Serializable[] after = row.getValue();
                        List<?> list =Arrays.stream(after).toList();
                        Long transactionId = Long.valueOf(list.get(1).toString());
                        this.transactionKafkaTemplate.send("transaction-topic",new TransactionResponse(200,"Success",transactionId));
                    }
                }
            }
        });
    }


}
