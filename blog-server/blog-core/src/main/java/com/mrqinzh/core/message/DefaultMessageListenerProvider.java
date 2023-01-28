package com.mrqinzh.core.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * todo 架构待优化
 * 生产消费模型
 */
@Component
public class DefaultMessageListenerProvider implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageListenerProvider.class);
    private static final Executor executor = Executors.newSingleThreadExecutor();
    private ApplicationContext applicationContext;
    private MessageListener listener;

    @Override
    public void afterPropertiesSet() throws Exception {
        listener = applicationContext.getBean(MessageListener.class);
        this.work();
    }

    private void work() {
        executor.execute(() -> {
            while (true) {
                try {
                    Message message = MessageQueue.consume();
                    if (message != null) {
                        logger.info("{} consuming !", message.getClass());
                        listener.consume(message);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
