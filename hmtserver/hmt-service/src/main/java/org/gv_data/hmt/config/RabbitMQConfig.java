package org.gv_data.hmt.config;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zhaoliang
 * @program :hmt_after
 * @description : rabbitMQ
 * @create : 2020/06/30 14:34
 */
@Configuration
public class RabbitMQConfig {


    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory mqConnectionFactory){
        SimpleRabbitListenerContainerFactory listenerContainerFactory=new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(mqConnectionFactory);
        //--加上这句
        listenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        return listenerContainerFactory;
    }

}

