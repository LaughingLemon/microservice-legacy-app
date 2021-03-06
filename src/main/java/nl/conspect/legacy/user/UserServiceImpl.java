/*
 * Copyright 2000-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.conspect.legacy.user;

import nl.conspect.legacy.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Marten Deinum
 */
@Transactional
@Service("userService")
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EventBus eventBus;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final EventBus eventBus) {
        this.userRepository = userRepository;
        this.eventBus = eventBus;
    }
    
    @Override
    public void save(User user) {
        userRepository.save(user);
        eventBus.emit(new UserCreatedEvent(user));
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
        eventBus.emit(new UserUpdatedEvent(user));
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findWithUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
