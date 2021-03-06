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

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by marten on 17-04-15.
 */
@Repository
class HibernateUserRepositoryImpl extends HibernateDaoSupport implements UserRepository {

    @Autowired
    public HibernateUserRepositoryImpl(SessionFactory sf) {
        super.setSessionFactory(sf);
    }
    
    @Override
    public void save(User user) {
        getHibernateTemplate().saveOrUpdate(user);
    }

    @Override
    public User find(long id) {
        return (User) getHibernateTemplate().get(User.class, id);
    }

    @Override
    public User findWithUsername(String username) {
        String hql = "from User u where u.username=:username";
        List users = getHibernateTemplate().findByNamedParam(hql, "username", username);
        return (User) DataAccessUtils.singleResult(users);
    }

    @Override
    public void remove(User user) {
        getHibernateTemplate().delete(user);
    }
}
