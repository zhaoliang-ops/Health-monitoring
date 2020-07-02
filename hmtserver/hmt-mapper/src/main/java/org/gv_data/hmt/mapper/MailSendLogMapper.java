package org.gv_data.hmt.mapper;

import org.apache.ibatis.annotations.Param;
import org.gv_data.hmt.model.MsgRevLog;

import java.util.Date;
import java.util.List;

public interface MailSendLogMapper {
    Integer updateMailSendLogStatus(@Param("msgId") String msgId, @Param("status") Integer status);

    Integer insert(MsgRevLog msgRevLog);

    List<MsgRevLog> getMailSendLogsByStatus();

    Integer updateCount(@Param("msgId") String msgId, @Param("date") Date date);
}
