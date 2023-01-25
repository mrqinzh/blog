package com.mrqinzh.core.message;

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
    private String content;

    public WebSocketMessage(String content, Integer... receiveIds) {
        BizAssert.notNull(receiveIds, "接收方信息不能为空");
        this.receiveIds = Arrays.asList(receiveIds);
        this.content = content;
    }

    public void addReceivers(Integer receiveId) {
        receiveIds.add(receiveId);
    }

}
