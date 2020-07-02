package org.gv_data.hmt.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.gv_data.hmt.model.SubMessage;
import org.gv_data.hmt.utils.POIUtils;
import org.springframework.amqp.core.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * @author : zhaoliang
 * @program :hmt_after
 * @description : Consumer
 * @create : 2020/06/30 18:25
 */
@Slf4j
@Component
public class MsgRevConsumer {
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("SubSystemLogQueue"))
    public void process(Message message){
        String messageBody = new String(message.getBody());
        log.info("messageBody"+messageBody);
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<SubMessage>>(){}.getType();

        ArrayList<SubMessage> subMessageArrayList = gson.fromJson(messageBody, userListType);
        for(SubMessage subMessage : subMessageArrayList) {
            String filepath = "subMessage/";
            log.info("子系统状态消息本地存档Excel-------开始-----------"+subMessage.getDate());
            POIUtils.subMessageExcel(subMessageArrayList,filepath);
            log.info("子系统状态消息本地存档Excel-------完成----------"+subMessage.getDate());
        }
    }
}
