package com.mybatis3.services;

import com.mybatis3.mappers.AggMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class AggServiceTest {

    @Autowired
    AggService aggService;
    @Test
    public void countByName() {
        List<Map<String, Integer>> maps = aggService.countByName();
        for (Map<String, Integer> map : maps) {
            System.out.println(map);
        }
    }
}