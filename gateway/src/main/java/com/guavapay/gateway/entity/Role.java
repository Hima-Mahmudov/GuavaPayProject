package com.guavapay.gateway.entity;

import lombok.Data;

import java.util.List;

@Data
public class Role extends BaseEntity {

    private String name;

    private List<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "id: " + super.getId() + ", " +
                "name: " + name + "}";
    }
}
