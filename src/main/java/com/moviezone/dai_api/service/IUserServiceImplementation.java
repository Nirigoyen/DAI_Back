package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.IUserDAOImplementation;
import com.moviezone.dai_api.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IUserServiceImplementation implements IUserService{

    @Autowired
    private IUserDAOImplementation IUserDAOImplementation;


    @Override
    public void createUser(User user) {
        IUserDAOImplementation.createUser(user);
    }

    @Override
    public void modifyUser(int userId, User user) {
        User currentUser = IUserDAOImplementation.findUserById(userId);
        currentUser.setName(user.getName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        currentUser.setBirthDate(user.getBirthDate());
        currentUser.setProfilePicture(user.getProfilePicture());

        IUserDAOImplementation.createUser(currentUser);

    }

    @Override
    public void deleteById(int userId) {
        IUserDAOImplementation.deleteUser(userId);
    }

    @Override
    public User findUserById(int userId) {
        return (IUserDAOImplementation.findUserById(userId));
    }
}
