package com.bezPalevaServer.Services;

import com.bezPalevaServer.db.User;
import com.bezPalevaServer.db.UserRepository;
import org.hibernate.annotations.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User getUserFromDB(long id){ return userRepository.findOne(id);}

    @Transactional
    public User getUserFromDB(int vkId){ return userRepository.getUserByVkID(vkId);}

    @Transactional
    public void addUserInBD(User user){ userRepository.save(user);}


}
