package com.example.UserManagement.model;


import com.example.UserManagement.listeners.MachineListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.BooleanFlag;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "Machines")
@EntityListeners({MachineListener.class})
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private MachineStatus status;

    @BooleanFlag
    private boolean active;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @DateTimeFormat(pattern="dd.MM.yyyy hh:mm:ss")
    private LocalDateTime dateCreated;
//    @JoinColumn(name = "user id", nullable = false)

    @ManyToOne
    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "ID", nullable = false)
    @JsonIgnore
    private User user;


}
