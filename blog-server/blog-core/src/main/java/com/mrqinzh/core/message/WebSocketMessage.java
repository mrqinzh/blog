package com.mrqinzh.core.message;

import cn.hutool.core.collection.CollectionUtil;
import com.mrqinzh.common.model.bean.WebSocketBean;
import com.mrqinzh.common.util.BizAssert;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@NoArgsConstructor
@ToString
@Data
public class WebSocketMessage implements Message {

    private Set<Integer> receiveIds = new HashSet<>();
    private WebSocketBean webSocketBean;

    public WebSocketMessage(WebSocketBean webSocketBean, Integer... receiveIds) {
        BizAssert.notNull(receiveIds, "接收方信息不能为空");
        this.receiveIds = CollectionUtil.newHashSet(receiveIds);
        this.webSocketBean = webSocketBean;
    }

    public void addReceivers(Integer receiveId) {
        receiveIds.add(receiveId);
    }

}
