package cn.wzy.service;

import cn.wzy.dao.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Create by Wzy
 * on 2018/6/24 16:52
 * 不短不长八字刚好
 */
public class KafkaTest extends BaseTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Test
    public void send() {
        kafkaTemplate.send("test",System.currentTimeMillis() + "","asdf");
    }
}
