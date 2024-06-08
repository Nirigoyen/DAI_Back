package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.entity.RefreshToken;

public interface IRefreshTokenService {
    public RefreshToken createRefreshToken(String userId, String accessToken);
    public RefreshToken findByRefreshToken(String token);
    public RefreshToken verifyRefreshTokenExpiration(RefreshToken refreshToken);
    public RefreshToken updateRefreshToken(RefreshToken refreshToken, String AccessToken);
    public void delete(RefreshToken refreshToken);
    public boolean findByUser(String userId);
    public void deleteByUser(String userId);

}
