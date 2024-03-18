package com.example.banking_transaction_service.service.service_impl.sync_service;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class BinLogService {

    private Logger logger = LoggerFactory.getLogger(BinLogService.class);

    public boolean ReadBinlog(String fileName,String startTime ,String stopTime,Long transactionId){
        try {
            logger.info("Start reading binary log");
            String transactionInLog = "###   @3=" + transactionId;
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "mysqlbinlog",
                    "-v",
                    "--base64-output=DECODE-ROW",
                    "--start-datetime=" + startTime,
                    "--stop-datetime=" + stopTime,
                    "\"C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\" + fileName + "\""
            );

            logger.info(processBuilder.toString());

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
//                if (line.equals(transactionInLog)){
//                    reader.close();
//                    process.destroy();
//                    logger.info("Find transaction id Succes");
//                    logger.info("Stop reading binary log");
//                    return true;
//                };
            }

            int exitCode = process.waitFor();
            reader.close();
            process.destroy();
            logger.info("Stop reading binary log");
            return false;
        } catch (IOException | InterruptedException e) {
            logger.error("Reading binary log fail: ",e.getMessage());
            return false;
        }
    }

}
