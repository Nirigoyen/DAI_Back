package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.FavMovie;
import com.moviezone.dai_api.model.entity.RefreshToken;
import com.moviezone.dai_api.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;


@Repository
public class UserDAOImplementation implements IUserDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public User findUserById(String userId) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<User> theQuery = currentSession.createQuery("FROM User WHERE userId=:userId", User.class);
        theQuery.setParameter("userId", userId);
        User user = theQuery.uniqueResult();
        return user;

//        User user = currentSession.get(User.class, userId);
//
//        return user;
    }

    @Override
    @Transactional
    public void createUser(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(user);

    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        Session currentSession = entityManager.unwrap(Session.class);

        System.err.println(userId);

        Query DeleteRefreshTokenQuery = currentSession.createQuery("DELETE FROM RefreshToken WHERE user.userId=:id");
        DeleteRefreshTokenQuery.setParameter("id", userId);
        DeleteRefreshTokenQuery.executeUpdate();

        Query DeleteFavourites = currentSession.createQuery("DELETE FROM FavMovie where user.userId=:id");
        DeleteFavourites.setParameter("id", userId);
        DeleteFavourites.executeUpdate();

        Query DeleteRatings = currentSession.createQuery("DELETE FROM Rating where user.userId=:id");
        DeleteRatings.setParameter("id", userId);
        DeleteRatings.executeUpdate();

        Query theQuery = currentSession.createQuery("DELETE FROM User WHERE userId=:id");
        theQuery.setParameter("id", userId);
        theQuery.executeUpdate();
    }

    @Override
    @Transactional
    public void updateUser(User user, String base64Img) {

        //? ACTUALIZAMOS LOS DATOS DEL USER EN LA BASE DE DATOS
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.update(user);

        if (base64Img != null) updateImg(user, base64Img);
    }

    private static void updateImg(User user, String base64Img) {
        //? ACTUALIZAMOS LA IMAGEN DE PERFIL EN EL OBS
        RestTemplate restTemplate = new RestTemplate();

        String URL = "https://dai-obs.obs.la-south-2.myhuaweicloud.com/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("key", "profile-pictures/" + user.getId() + ".jpg");


        //! SACAMOS LA METADATA DE LA BASEIMG ( data:image/jpg;base64, )
        String partSeparator = ",";
        if (base64Img.contains(partSeparator)) {
            base64Img = base64Img.split(partSeparator)[1];
        }

        //* CONVERTIMOS LA IMAGEN DE BASE64 A JPG Y LA AGREGAMOS A LA REQUEST
        byte[] imageBytes = Base64.getDecoder().decode(base64Img);
        ByteArrayResource imgResource = new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return user.getId() + ".jpg";
            }
        };


        formData.add("file", imgResource);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);


    }

//    @Override
//    public List<FavMovie> getUserFavs(String userId){
//
//        Session currentSession = entityManager.unwrap(Session.class);
//        Query<FavMovie> theQuery = currentSession.createQuery("SELECT u.FavMovie FROM User u WHERE u.userId = :userId", FavMovie.class);
//        theQuery.setParameter("userId", userId);
//        List<FavMovie> favs = theQuery.getResultList();
//        return favs;
//    }

}
