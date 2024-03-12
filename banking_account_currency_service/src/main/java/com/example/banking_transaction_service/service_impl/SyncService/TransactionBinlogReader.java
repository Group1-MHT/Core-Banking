//package com.example.banking_transaction_service.service_impl.SyncService;
//
//
//import com.example.banking_transaction_service.dto.TransactionDTO;
//import com.github.shyiko.mysql.binlog.BinaryLogClient;
//import com.github.shyiko.mysql.binlog.event.Event;
//import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
//
//import java.util.Arrays;
//
//public class TransactionBinlogReader {
//
//    public boolean findTransactionInBinlog(TransactionDTO transactionDTO) {
//        try (BinaryLogClient client = new BinaryLogClient(
//                "localhost",  // Replace with your MySQL hostname
//                3306,        // Replace with your MySQL port
//                "root",  // Replace with your MySQL username
//                "29082001"   // Replace with your MySQL password
//        )) {
//            client.registerEventListener(event -> {
//                if (event.getHeader().getEventType() == WriteRowsEventData.EVENT_TYPE) {
//                    WriteRowsEventData data = event.getData();
//                    if ("balances".equals(data.getTableId()) &&
//                            Arrays.stream(data.getRows()).anyMatch(row ->
//                                    transactionDTO.getId().equals(row[0]))) {
//                        return true; // Transaction found, stop reading binlog
//                    }
//                }
//            });
//
//            client.connect();
//            return false; // Transaction not found in the initial binlog events
//        } catch (Exception e) {
//            throw new RuntimeException("Error reading binlog", e);
//        }
//    }
//}
