package com.qin.service.Impl;

import com.qin.mapper.MsgMapper;
import com.qin.pojo.LeaveMsg;
import com.qin.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private MsgMapper msgMapper;

    @Override
    public List<LeaveMsg> findAllMsg() {

        return msgMapper.findAllMsg();
    }

    @Override
    public int addMsg(LeaveMsg leaveMsg) {
        return msgMapper.addMsg(leaveMsg);
    }
}
