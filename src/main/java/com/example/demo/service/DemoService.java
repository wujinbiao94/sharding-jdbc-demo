package com.example.demo.service;


import com.example.demo.commn.GenerateID;
import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.UserInfoMapper;
import groovy.util.logging.Slf4j;
import io.shardingjdbc.core.api.HintManager;
import io.shardingjdbc.core.hint.HintManagerHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class DemoService {

    @Resource
    UserInfoMapper userInfoMapper;

    public static Long userId = 150L;



    public void demo() {
        if (GenerateID.maxId == 0){
            GenerateID.queryId(10);
        }
        userId = GenerateID.minId;
        System.out.println("Insert--------------");
        for (int i = 1; i <= 10; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setAccount("Account" + i);
            userInfo.setPassword("pass" + i);
            userInfo.setUserName("name" + i);
            userId++;
            userInfoMapper.insert(userInfo);
            if (userId.equals(GenerateID.maxId)){
                GenerateID.queryId(10);
                userId = GenerateID.minId;
            }
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
