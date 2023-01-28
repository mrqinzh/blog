package com.mrqinzh.core.message;

import com.mrqinzh.common.model.bean.WebSocketBean;
import com.mrqinzh.common.util.BizAssert;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@ToString
@Data
public class WebSocketMessage implements Message {

    private List<Integer> receiveIds = new ArrayList<>();
    private WebSocketBean webSocketBean;

    public WebSocketMessage(WebSocketBean webSocketBean, Integer... receiveIds) {
        BizAssert.notNull(receiveIds, "接收方信息不能为空");
        this.receiveIds = Arrays.asList(receiveIds);
        this.webSocketBean = webSocketBean;
    }

    public void addReceivers(Integer receiveId) {
        receiveIds.add(receiveId);
    }

}
