package com.moviezone.dai_api.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.moviezone.dai_api.model.dto.ProfilePictureDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Arrays;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    //Relaciones
    @OneToOne()
    @JsonBackReference(value = "user-profile_picture")
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    public ProfilePicture(byte[] data, User user) {
        super();
        this.data = data;
        this.user = user;
    }

    public ProfilePicture() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ProfilePicture{" +
                "id=" + id +
                ", data=" + Arrays.toString(data) +
                ", user=" + user +
                '}';
    }

    public ProfilePictureDTO toDTO() {
        return new ProfilePictureDTO(this.id, this.data);
    }
}
