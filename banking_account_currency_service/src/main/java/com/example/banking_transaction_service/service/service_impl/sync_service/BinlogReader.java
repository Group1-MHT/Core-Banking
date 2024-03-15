//package com.example.banking_transaction_service.service_impl.sync_service;
//
//
//import com.github.shyiko.mysql.binlog.BinaryLogClient;
//import com.github.shyiko.mysql.binlog.event.EventData;
//import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
//import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.List;
//import java.util.Map;
//
//public class BinlogReader {
//
//    String hostname = "your-mysql-hostname";
//    int port = 3306; // replace with your MySQL port
//    String username = "your-mysql-username";
//    String password = "your-mysql-password";
//    String databaseName = "your-database-name";
//
//    // Replace with the TransactionDTO ID you want to filter
//    Long transactionDtoIdToFilter = 123L;
//
//
//    public static boolean isTransactionDtoIdPresentInBinlog(
//            String hostname, int port, String username, String password,
//            String databaseName, Long transactionDtoIdToFilter) throws IOException {
//        BinaryLogClient client = new BinaryLogClient(hostname, port, username, password);
//
//        // Set up a custom event deserializer to process different event types
//        EventDeserializer eventDeserializer = new EventDeserializer();
//        eventDeserializer.setCompatibilityMode(EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG);
//        client.setEventDeserializer(eventDeserializer);
//
//        // TransactionDTO ID presence flag
//        boolean isTransactionDtoIdPresent = false;
//
//        client.registerEventListener(event -> {
//            EventData data = event.getData();
//            if (data instanceof UpdateRowsEventData) {
//                UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;
//                List<Map.Entry<Serializable[], Serializable[]>> rows = updateRowsEventData.getRows();
//                for (Map.Entry<Serializable[], Serializable[]> row : rows) {
//                    // Assuming the structure of the row matches your "TransactionDTO" table
//                    Long transactionDtoId = (Long) row[0]; // Adjust the index based on your actual column order
//
//                    if (transactionDtoId.equals(transactionDtoIdToFilter)) {
//                        isTransactionDtoIdPresent = true;
//                        break;
//                    }
//                }
//            }
//        });
//
//        client.connect();
//
//        try {
//            Thread.sleep(60000); // Adjust as needed
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        client.disconnect();
//
//        return isTransactionDtoIdPresent;
//    }
//}
//
