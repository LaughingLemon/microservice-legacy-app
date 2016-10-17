/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.user;

import nl.conspect.legacy.event.AbstractDomainEvent;

/**
 *
 * @author Shaun
 */
public abstract class AbstractUserEvent extends AbstractDomainEvent {
    
    public AbstractUserEvent(Object source) {
        super(source);
    }
    
    public User getUser() {
        return (User) getSource();
    }
    
}
