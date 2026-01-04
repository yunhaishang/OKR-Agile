package com.se.okr_agile.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.se.okr_agile.entity.MetricSnapshot;
import com.se.okr_agile.mapper.MetricSnapshotMapper;
import com.se.okr_agile.service.MetricSnapshotService;
import org.springframework.stereotype.Service;

@Service
public class MetricSnapshotServiceImpl extends ServiceImpl<MetricSnapshotMapper, MetricSnapshot> implements MetricSnapshotService {
}