package com.mybatis3.services;

import com.mybatis3.mappers.AggMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AggService {
    @Autowired
    AggMapper aggMapper;
    List<Map<String, Integer>> countByName() {
        return aggMapper.countByName();
    }
}
