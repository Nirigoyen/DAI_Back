package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.entity.RefreshToken;

public interface IRefreshTokenService {
    public RefreshToken createRefreshToken(String userId);
    public RefreshToken findByRefreshToken(String token);
    public RefreshToken verifyRefreshTokenExpiration(RefreshToken refreshToken);
    public void delete(RefreshToken refreshToken);

}
