package cn.wzy.judge;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

/**
 * Create by Wzy
 * on 2018/6/24 16:49
 * 不短不长八字刚好
 */
public class SubmitConsumer implements MessageListener<String,String> {
    @Override
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        System.out.println("收到消息" + consumerRecord.value());
    }
}
