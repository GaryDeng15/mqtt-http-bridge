package com.gary.mqtthttpbridge.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gary.mqtthttpbridge.service.MqttDataSaveService;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.nio.charset.StandardCharsets;

@Configuration
public class MqttInboundConfiguration {

    @Autowired
    private MqttConfig mqttConfiguration;

    @Autowired
    private MqttDataSaveService mqttDataSaveService;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttConfiguration.getUrl()});
        options.setCleanSession(false);
        options.setUserName(mqttConfiguration.getUsername());
        options.setPassword(mqttConfiguration.getPassword().toCharArray());
        options.setConnectionTimeout(30);
        options.setKeepAliveInterval(60);
        options.setAutomaticReconnect(true);
        options.setMaxReconnectDelay(10000);
        factory.setConnectionOptions(options);
        return factory;
    }



    @Bean
    public MessageProducer inbound() {
        // 使用通配符订阅三级主题
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                mqttConfiguration.getClient() + "_inbound",
                mqttClientFactory(),
                mqttConfiguration.getReceiveTopics().split(",")
        );
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(mqttConfiguration.getQos());  // 设置QoS级别
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler messageHandler() {
        return message -> {
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            String strPayload = (String) message.getPayload();
            byte[] bytePayload = strPayload.getBytes(StandardCharsets.UTF_8);
            // ...处理byte数组
            String payload = new String((byte[]) bytePayload);
            System.out.printf("收到消息 -> [主题:%s] [内容:%s]%n \n==================================================================", topic, payload);

            // TODO在这里添加业务处理逻辑
            mqttDataSaveService.saveData(payload);

        };
    }
}
