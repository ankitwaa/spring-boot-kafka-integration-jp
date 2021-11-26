package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.domain.BatchFeedEvent;
import com.example.springbootkafkaintegrationjp.domain.KafkaMessageProcessedInfo;
import com.example.springbootkafkaintegrationjp.integration.ChannelGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class FileWriterServiceActivator {

    @Autowired
    private ChannelGateway channelGateway;

    @Value("${feed.temp.dir}")
    private String temFileDirectory;

    private ConcurrentHashMap<String, KafkaMessageProcessedInfo> fileMap = new ConcurrentHashMap<>();


    @ServiceActivator(inputChannel = "tempFileCreationChannel", outputChannel = "nullChannel")
    public void process(BatchFeedEvent batchFeedEventMessage) throws IOException {
        log.info("received batch event:{}", batchFeedEventMessage.getFeedEventList());

        String fileKey = batchFeedEventMessage.getFeedEventList().getPayload().get(0).getOrderId();

        KafkaMessageProcessedInfo path = fileMap.get(fileKey);
        if (path == null) {
            String filePath = temFileDirectory + File.separator + fileKey;
            log.info("writing to file:{}", filePath);
            try (FileWriter fileWriter = new FileWriter(filePath, true)) {
                KafkaMessageProcessedInfo path1 = new KafkaMessageProcessedInfo();
                batchFeedEventMessage.getFeedEventList().getPayload().forEach(feedEventMessage -> {
                    try {
                        fileWriter.write(feedEventMessage.getOrderId() + "|" + feedEventMessage.getKey() + "|" +
                                feedEventMessage.getValue() + "\n");
                        path1.setMessageCount(batchFeedEventMessage.getFeedEventList().getPayload().size() + 1);
                        path1.setFilePath(filePath);
                        if (path1.getMessageCount() == feedEventMessage.getCount()) {
                            log.info("all message has been received for feed data for this file - Completed");
                        }
                        fileMap.put(fileKey, path1);
                    } catch (IOException e) {
                        log.error("error while opening file :{}", filePath, filePath);
                    }
                });
            } catch (Exception exception) {
                log.error("error while opening file :{}", filePath, filePath);
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(path.getFilePath(), true)) {
                final KafkaMessageProcessedInfo path1 = path;
                batchFeedEventMessage.getFeedEventList().getPayload().forEach(feedEventMessage -> {
                    try {
                        fileWriter.write(feedEventMessage.getOrderId() + "|" + feedEventMessage.getKey() + "|" +
                                feedEventMessage.getValue() + "\n");
                        path1.setMessageCount(batchFeedEventMessage.getFeedEventList().getPayload().size() + 1);
                        if (path1.getMessageCount() == feedEventMessage.getCount()) {
                            log.info("all message has been received for feed data for this file - Completed");
                        }
                        fileMap.put(fileKey, path1);
                    } catch (IOException e) {
                        log.error("error while opening file :{}", path.getFileName(), e);
                    }
                });
            } catch (Exception exception) {
                log.error("error while opening file :{}");
            }
        }


    }

}
