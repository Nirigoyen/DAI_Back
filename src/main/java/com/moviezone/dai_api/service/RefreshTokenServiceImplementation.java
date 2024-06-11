package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dao.IRefreshTokenDAO;
import com.moviezone.dai_api.model.dao.IUserDAO;
import com.moviezone.dai_api.model.dto.AuthResponseDTO;
import com.moviezone.dai_api.model.entity.RefreshToken;
import com.moviezone.dai_api.utils.SaltUtil;
import com.moviezone.dai_api.utils.TokenEncrypter;
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

    private final int REFRESH_TOKEN_EXPIRATION_TIME = 60 * 1000 * 60 * 24; // 5 MINUTES
    private final int SALT_LENGTH = 16;

    @Override
    public AuthResponseDTO createRefreshToken(String userId, String accessToken) { //! ESTA FUNCION CREA Y GUARDA EL TOKEN

        String salt = SaltUtil.generateSalt(SALT_LENGTH);
        String refreshTokenToken = UUID.randomUUID().toString(); //* EL REFRESH TOKEN COMO TAL

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userDAO.findUserById(userId));
        refreshToken.setExpiresAt(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_TIME));

        //* SETTEAMOS LOS TOKENS Y LOS ENCRIPTAMOS USANDO LA SALT ( SE GENERA UNA NUEVA SALT CADA VEZ QUE SE HACE UN NUEVO TOKEN)
        //* PERO LA PEPPER ES SIEMPRE LA MISMA
        //* TAMBIÉN ES IMPORTANTE GUARDAR LA SALT PARA LUEGO UTILIZARLA PARA
        refreshToken.setToken(TokenEncrypter.encryptToken(refreshTokenToken, salt));
        refreshToken.setAccessToken(TokenEncrypter.encryptToken(accessToken, salt));
        refreshToken.setSalt(salt);

        //* GUARDAMOS LOS TOKENS ENCRIPTADOS Y TODA LA INFO EN LA BD
        refreshTokenDAO.save(refreshToken);

        //* DEVOLVEMOS ACCESS TOKEN Y REFRESH TOKEN SIN ENCRIPTAR
        return new AuthResponseDTO(accessToken, refreshTokenToken);

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
    public AuthResponseDTO updateRefreshToken(RefreshToken refreshToken, String accessToken) {

        String salt = SaltUtil.generateSalt(SALT_LENGTH);
        String refreshTokenToken = UUID.randomUUID().toString(); //* EL REFRESH TOKEN COMO TAL

        RefreshToken oldRefreshToken = refreshTokenDAO.findByRefreshToken(refreshToken.getToken());
        if (oldRefreshToken != null) {
            //* SI LO PERSISTIDO EXISTE, LO UPDATEAMOS ENCRIPTANDO LOS NUEVOS TOKENS QUE DEBEMOS DEVOLVER
            oldRefreshToken.setToken(TokenEncrypter.encryptToken(refreshTokenToken, salt));
            oldRefreshToken.setAccessToken(TokenEncrypter.encryptToken(accessToken, salt));
            oldRefreshToken.setSalt(salt);
            refreshTokenDAO.save(oldRefreshToken);

            return new AuthResponseDTO(accessToken, refreshTokenToken);
        }
        return null;
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenDAO.delete(refreshToken);
    }

    @Override
    public RefreshToken findByUser(String userId) {
        return refreshTokenDAO.findByUser(userId);
    }

    @Override
    public void deleteByUser(String userId) {
        refreshTokenDAO.deleteByUser(userId);
    }
}
