package com.example.pl_lab_server.Common.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Getter
@Setter
@Component
public class BaseUtil {
    public LocalDateTime now = LocalDateTime.now();
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDate today = LocalDate.now();
    public String todayANDtime = now.format(formatter);
}
