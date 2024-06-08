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

    private final int REFRESH_TOKEN_EXPIRATION_TIME = 60 * 1000 * 5; // 5 MINUTES


    @Override
    public RefreshToken createRefreshToken(String userId, String accessToken) { //! ESTA FUNCION CREA Y GUARDA EL TOKEN

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userDAO.findUserById(userId));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_TIME));
        refreshToken.setAccessToken(accessToken);

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
    public RefreshToken updateRefreshToken(RefreshToken refreshToken, String accessToken) {

        RefreshToken oldRefreshToken = refreshTokenDAO.findByRefreshToken(refreshToken.getToken());
        if (oldRefreshToken != null) {
            oldRefreshToken.setToken(UUID.randomUUID().toString());
            oldRefreshToken.setAccessToken(accessToken);
            refreshTokenDAO.save(oldRefreshToken);

            return refreshToken;
        }
        return null;
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
