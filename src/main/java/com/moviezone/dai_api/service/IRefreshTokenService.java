package com.moviezone.dai_api.service;

import com.moviezone.dai_api.model.dto.AuthResponseDTO;
import com.moviezone.dai_api.model.entity.RefreshToken;

public interface IRefreshTokenService {
    public AuthResponseDTO createRefreshToken(String userId, String accessToken);
    public RefreshToken findByRefreshToken(String token);
    public RefreshToken verifyRefreshTokenExpiration(RefreshToken refreshToken);
    public AuthResponseDTO updateRefreshToken(RefreshToken refreshToken, String AccessToken);
    public void delete(RefreshToken refreshToken);
    public RefreshToken findByUser(String userId);
    public void deleteByUser(String userId);

}
