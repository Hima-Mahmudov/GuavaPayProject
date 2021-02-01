package com.guavapay.auth.entity;

import lombok.Data;

@Data
public class Role extends BaseEntity {

    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "id: " + super.getId() + ", " +
                "name: " + name + "}";
    }
}
