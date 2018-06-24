package cn.wzy.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringEncoder;

/**
 * Create by Wzy
 * on 2018/6/24 14:44
 * 不短不长八字刚好
 */
public class Consumer2 {
    public static Properties props;
    static {
        props = new Properties();
        props.put("zookeeper.connect", "192.168.0.115:2181");
        props.put("serializer.class", StringEncoder.class.getName());
        props.put("metadata.broker.list", "192.168.0.115:9092");
        props.put("group.id", "group");
        props.put("group.name", "2");
    }

    public static void main(String[] args) throws InterruptedException {
        String topic = "test";
        ConsumerConnector consumer = Consumer
                .createJavaConsumerConnector(new ConsumerConfig(props));
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, 1); // 取哪一个topic 取几条数据
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer
                .createMessageStreams(topicCountMap);
        final KafkaStream<byte[], byte[]> kafkaStream = messageStreams.get(
                topic).get(0);
        ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
        while (iterator.hasNext()) {
            String item = new String(iterator.next().message());
            System.out.println("收到消息：" + item);
            Thread.sleep(2000);
        }
    }
}
