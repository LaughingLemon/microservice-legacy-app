/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.event;

/**
 *
 * @author Shaun
 */
public interface EventBus {
    
    void emit(DomainEvent event);
    
}
