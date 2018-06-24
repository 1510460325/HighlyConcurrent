package cn.wzy.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;
import java.util.concurrent.Executors;

/**
 * Create by Wzy
 * on 2018/6/24 14:29
 * 不短不长八字刚好
 */
public class ProducerDemo{
    private Producer<String,String> producer;

    public static void main(String[] args) {
        new ProducerDemo().start();
    }

    public void init(){
        Properties props = new Properties();
        props.put("metadata.broker.list", "192.168.0.115:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);
        producer = new Producer<>(config);
    }

    public void produceMsg(){
        // 构建发送的消息
        long timestamp = System.currentTimeMillis();
        String msg = "Msg" + timestamp;
        String topic = "test";  // 确保有这个topic
        System.out.println("发送消息" + msg);
        String key = "Msg-Key" + timestamp;

        /**
         * topic: 消息的主题
         * key：消息的key，同时也会作为partition的key
         * message:发送的消息
         */
        KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, key, msg);

        producer.send(data);
    }

    public void start() {
        System.out.println("开始发送消息 ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                init();
                while (true) {
                    try {
                        produceMsg();
                        Thread.sleep(2000);
                    } catch (Throwable e) {
                        if (producer != null) {
                            try {
                                producer.close();
                            } catch (Throwable e1) {
                                System.out.println("Turn off Kafka producer error! " + e);
                            }
                        }
                    }

                }

            }
        });
    }
}
