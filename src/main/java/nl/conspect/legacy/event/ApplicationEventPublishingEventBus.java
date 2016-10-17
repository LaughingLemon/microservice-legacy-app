/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author Shaun
 */
@Component("eventBus")
public class ApplicationEventPublishingEventBus implements EventBus, ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;
    
    @Override
    public void emit(DomainEvent event) {
        if (event instanceof ApplicationEvent) {
            eventPublisher.publishEvent((ApplicationEvent) event);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher aep) {
        eventPublisher = aep;
    }
    
}
