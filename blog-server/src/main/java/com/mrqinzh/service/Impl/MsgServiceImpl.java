package com.mrqinzh.service.Impl;

import com.mrqinzh.mapper.MsgMapper;
import com.mrqinzh.entity.LeaveMsg;
import com.mrqinzh.service.MsgService;
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
