package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.IRefreshTokenDAO;
import com.moviezone.dai_api.model.dao.IUserDAO;
import com.moviezone.dai_api.model.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImplementation implements IRefreshTokenService{

    @Autowired
    private IRefreshTokenDAO refreshTokenDAO;
    @Autowired
    private IUserDAO userDAO;


    @Override
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userDAO.findUserById(userId));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plusMillis(60 * 1000 * 5));

        refreshTokenDAO.save(refreshToken);
        return refreshToken;

    }

    @Override
    public RefreshToken findByRefreshToken(String token) {
        return refreshTokenDAO.findByRefreshToken(token);
    }

    @Override
    public RefreshToken verifyRefreshTokenExpiration(RefreshToken refreshToken) {
        if(refreshToken.getExpiresAt().compareTo(Instant.now()) < 0) {
            refreshTokenDAO.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + "REFRESH TOKEN WAS EXPIRED");
        }
        return refreshToken;
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenDAO.delete(refreshToken);
    }

    @Override
    public boolean findByUser(String userId) {
        return refreshTokenDAO.findByUser(userId);
    }

    @Override
    public void deleteByUser(String userId) {
        refreshTokenDAO.deleteByUser(userId);
    }
}
