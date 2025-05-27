package com.ecommerce.usermanagementservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sessions")
public class Session extends BaseModel{
    private String token;

    @ManyToOne
    private User user;
}
