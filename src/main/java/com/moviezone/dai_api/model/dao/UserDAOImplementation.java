package com.moviezone.dai_api.model.dao;

import com.moviezone.dai_api.model.entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Session;
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

        User user = currentSession.get(User.class, userId);

        return user;
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
        currentSession.remove(findUserById(userId));
    }

    @Override
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
        headers.setContentType(MediaType.IMAGE_JPEG);

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("key", user.getId() + ".jpg");

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

        ResponseEntity<String> response = restTemplate.exchange(user.getProfilePicture(), HttpMethod.POST, entity, String.class);
    }

}
