package com.example.demo.service;


import com.example.demo.commn.GenerateID;
import com.example.demo.entity.OrderInfo;
import com.example.demo.mapper.OrderInfoMapper;
import groovy.util.logging.Slf4j;
import io.shardingjdbc.core.api.HintManager;
import io.shardingjdbc.core.hint.HintManagerHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class DemoService {

    @Resource
    OrderInfoMapper orderInfoMapper;

    public static Long orderId = 150L;



    public void demo() {
        if (GenerateID.maxId == 0){
            GenerateID.queryId(10);
        }
        orderId = GenerateID.minId;
        System.out.println("Insert--------------");
        for (int i = 1; i <= 10; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(orderId);
            orderInfo.setAccount("Account" + i);
            orderInfo.setPassword("pass" + i);
            orderInfo.setUserName("name" + i);
            orderInfoMapper.insert(orderInfo);
            if (orderId.equals(GenerateID.maxId)){
                GenerateID.queryId(10);
                orderId = GenerateID.minId;
            }
            orderId++;
        }
        System.out.println("over..........");
    }


    private void getUserId(){
        HintManagerHolder.clear();
        HintManager hintManager = HintManager.getInstance();
        hintManager.addDatabaseShardingValue("user_info", "user_id", 3L);
        hintManager.addTableShardingValue("user_info", "user_id", 3L);
    }
}
