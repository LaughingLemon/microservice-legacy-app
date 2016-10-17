/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.conspect.legacy.user;

/**
 *
 * @author Shaun
 */
public class UserUpdatedEvent extends AbstractUserEvent {

    public UserUpdatedEvent(Object source) {
        super(source);
    }

    @Override
    public long getTimeStamp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
