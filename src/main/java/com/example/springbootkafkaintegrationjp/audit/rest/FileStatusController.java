package com.example.springbootkafkaintegrationjp.audit.rest;

import com.example.springbootkafkaintegrationjp.audit.dto.FileStatus;
import com.example.springbootkafkaintegrationjp.audit.repo.FileMetricEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileStatusController {

    @Autowired
    private FileMetricEventRepo fileMetricEventRepo;

    @GetMapping("/status")
    private List<FileStatus> fileStatusList(){
        return fileMetricEventRepo.findAll().stream().map(fm -> {
            FileStatus fileStatus = new FileStatus();
            fileStatus.setStatus(fm.getStatus());
            fileStatus.setMessageCount(fm.getExpectedCount()+"");
            fileStatus.setMessageCountReceived(fm.getReceivedCount()+"");
            fileStatus.setFilename(fm.getFileName());
            return fileStatus;
        }).collect(Collectors.toList());
    }

}
