package com.github.stodorov.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    String name;

    public Role(String name) {
        this.name = name;
    }
}
