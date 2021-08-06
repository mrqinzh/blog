package com.qin.mapper;

import com.qin.pojo.LeaveMsg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MsgMapper {

    List<LeaveMsg> findAllMsg(); // 查看所有用户的留言

    int addMsg(LeaveMsg leaveMsg); // 新增一条留言记录

}
