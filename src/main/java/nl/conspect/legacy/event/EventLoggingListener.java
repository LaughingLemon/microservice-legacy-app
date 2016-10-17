/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Shaun
 */
@Component
public class EventLoggingListener implements ApplicationListener<AbstractDomainEvent> {

    private final Logger logger = LoggerFactory.getLogger(EventLoggingListener.class);

    @Override
    public void onApplicationEvent(AbstractDomainEvent e) {
        logger.info("Event: {}", e);
    }

}
