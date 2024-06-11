package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.RefreshToken;

public interface IRefreshTokenDAO {
    public void save(RefreshToken refreshToken);
    public RefreshToken findByRefreshToken(String refreshToken);
    public void delete(RefreshToken refreshToken);
    public RefreshToken findByUser(String userId);
    public void deleteByUser(String userId);

}
