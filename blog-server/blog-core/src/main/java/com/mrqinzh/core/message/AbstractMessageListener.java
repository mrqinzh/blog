package com.mrqinzh.core.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 生产消费模型
 */
@Component
public abstract class AbstractMessageListener<T extends Message> implements InitializingBean, MessageListener<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractMessageListener.class);
    private static final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.work();
    }

    private void work() {
        executor.execute(() -> {
            while (true) {
                Message message = MessageQueue.consume();
                if (message != null) {
                    logger.info("{} consuming !", message.getClass());
                    this.onMessage((T) message);
                }
            }
        });
    }

}
