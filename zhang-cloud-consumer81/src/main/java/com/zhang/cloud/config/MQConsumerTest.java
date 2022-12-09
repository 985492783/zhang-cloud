package com.zhang.cloud.config;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.zhang.cloud.consumer.ConsumerService;
import com.zhang.cloud.consumer.FactoryService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 98549
 * @date 2022/1/5 18:02
 */
@Configuration
public class MQConsumerTest extends MQConsumerConfigure{
    @Resource
    private MQConsumeMsgListenerProcessor consumeMsgListenerProcessor;

    @Override
    @Bean
    @ConditionalOnProperty(prefix="rocketmq.consumer",name = "isOnOff",havingValue = "true")
    public DefaultMQPushConsumer defaultConsumer() {
        LOGGER.info("defaultConsumer 正在创建---------------------------------------");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        // 设置监听
        consumer.registerMessageListener(consumeMsgListenerProcessor);

        /**
         * 设置consumer第一次启动是从队列头部开始还是队列尾部开始
         * 如果不是第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
        try {
            // 设置该消费者订阅的主题和tag，如果订阅该主题下的所有tag，则使用*,
            consumer.subscribe("shop", "*");
            consumer.start();
            LOGGER.info("consumer 创建成功--------------------------------------- ");
        } catch (MQClientException e) {
            LOGGER.error("consumer 创建失败!");
        }
        return consumer;
    }
    @Component
    private class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {

        @Resource
        private FactoryService factoryService;
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
            if (CollectionUtils.isEmpty(msgList)) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            MessageExt messageExt = msgList.get(0);
            String body=null;
            try {
                body = new String(messageExt.getBody(), "utf-8");
            } catch (Exception e) {
                LOGGER.error("获取MQ消息内容异常 {}",e);
            }

            // TODO 处理业务逻辑
            ConsumerService service = factoryService.getService(messageExt.getTags());
            if(body!=null &&service!=null){
                service.invoke(body);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
