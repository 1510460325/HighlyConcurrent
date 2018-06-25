package cn.wzy.judge;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

/**
 * Create by Wzy
 * on 2018/6/24 16:46
 * 不短不长八字刚好
 */
public class SubmitProduce implements ProducerListener {
    @Override
    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        System.out.println("send success...");
    }

    @Override
    public void onError(String topic, Integer partition, Object key, Object value, Exception exception) {
        System.out.println("send failed...");
    }
}
