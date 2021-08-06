package com.qin.service;

import com.qin.pojo.LeaveMsg;

import java.util.List;

public interface MsgService {

    List<LeaveMsg> findAllMsg(); // 查看所有用户的留言

    int addMsg(LeaveMsg leaveMsg); // 新增一条留言记录
}
