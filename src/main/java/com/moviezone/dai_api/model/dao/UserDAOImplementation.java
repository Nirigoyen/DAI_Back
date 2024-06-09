package com.moviezone.dai_api.model.dao;

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
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;


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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("key", user.getId() + ".jpg");

        System.err.println("ARRIBA DE LA DECODIFICACION");

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

        System.err.println("ABAJO DE LA DECODIFICACION");

        formData.add("file", imgResource);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<String> response = restTemplate.exchange(user.getProfilePicture(), HttpMethod.POST, entity, String.class);

        //ResponseEntity<String> response = restTemplate.exchange(user.getProfilePicture(), HttpMethod.POST, entity, String.class);

        System.err.println("SE MANDO LA IMG");
    }

}
