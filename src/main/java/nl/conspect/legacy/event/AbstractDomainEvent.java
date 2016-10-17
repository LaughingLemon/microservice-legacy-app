/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.event;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Shaun
 */
public abstract class AbstractDomainEvent extends ApplicationEvent implements DomainEvent {

    public AbstractDomainEvent(Object source) {
        super(source);
    }
    
}
