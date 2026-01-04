package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.RetrospectiveReport;
import com.se.okr_agile.mapper.RetrospectiveReportMapper;
import com.se.okr_agile.service.RetrospectiveReportService;
import org.springframework.stereotype.Service;

@Service
public class RetrospectiveReportServiceImpl extends ServiceImpl<RetrospectiveReportMapper, RetrospectiveReport> implements RetrospectiveReportService {
}