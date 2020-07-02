package org.gv_data.hmt.task;
import lombok.extern.slf4j.Slf4j;
import org.gv_data.hmt.model.SubMessage;
import org.gv_data.hmt.utils.POIUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class MsgRevTask {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Scheduled(cron = "0/10 * * * * ?")
    public void handleExcelMessageTask() throws Exception {

        List<SubMessage> subMessages = new ArrayList<>();
        String filePath = "subMessage/";
        File file = new File(filePath);
        String[] filelist = file.list();
        if (file.list()!=null){
            for (int i = 0; i < Objects.requireNonNull(filelist).length ; i++) {
                File file1 = new File(filePath + filelist[i]);
                log.info("解析文件"+file1.getName()+"--------开始--------");
                List<SubMessage> subMessageList = POIUtils.excel2SubMessage(file1);
                subMessages.addAll(subMessageList);
                log.info("解析文件"+file1.getName()+"--------结束--------");
                for (SubMessage sub:subMessages) {
                    log.debug("子系统消息开始处理入库---------" + sub.toString() + "--------------------------");
                }
                log.info("删除本地备份文件"+file1.getName()+"--------开始--------");
                file1.delete();
                log.info("删除本地备份文件"+file1.getName()+"--------结束--------");
            }
        }
//        subMessages.forEach(subMessage -> {
//            log.debug("子系统消息开始处理入库---------"+subMessages.toString()+"--------------------------");
//        });
    }
}
