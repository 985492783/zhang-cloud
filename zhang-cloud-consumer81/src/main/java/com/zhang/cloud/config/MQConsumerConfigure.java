package com.zhang.cloud.config;

import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 98549
 * @date 2022/1/4 22:06
 */
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq.consumer")
public abstract class MQConsumerConfigure {
    public static final Logger LOGGER = LoggerFactory.getLogger(MQConsumerConfigure.class);
    protected String groupName;
    protected String namesrvAddr;
    protected String topics;
    protected Integer consumeThreadMin;
    protected Integer consumeThreadMax;
    protected Integer consumeMessageBatchMaxSize;

    public abstract DefaultMQPushConsumer defaultConsumer();
}
