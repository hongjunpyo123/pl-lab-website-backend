package com.example.pl_lab_server.apply.application;

import com.example.pl_lab_server.Common.util.BaseUtil;
import com.example.pl_lab_server.Common.util.JWTutil;
import com.example.pl_lab_server.Common.util.SecurityUtil;
import com.example.pl_lab_server.apply.domain.repository.ApplyPersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyPersonalInfoManage {
    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private BaseUtil baseUtil;

    @Autowired
    private ApplyPersonalInfoRepository applyPersonalInfoRepository;
}
