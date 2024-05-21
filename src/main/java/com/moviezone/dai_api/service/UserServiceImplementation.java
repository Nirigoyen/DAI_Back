package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.UserDAOImplementation;
import com.moviezone.dai_api.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements IUserService{

    @Autowired
    private UserDAOImplementation userDAO;


    @Override
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    @Override
    public void modifyUser(int userId, User user) {
        User currentUser = userDAO.findUserById(userId);
        currentUser.setName(user.getName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        currentUser.setBirthDate(user.getBirthDate());
        currentUser.setProfilePicture(user.getProfilePicture());

        userDAO.createUser(currentUser);

    }

    @Override
    public void deleteById(int userId) {
        userDAO.deleteUser(userId);
    }

    @Override
    public User findUserById(int userId) {
        return (userDAO.findUserById(userId));
    }
}
