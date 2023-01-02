package org.abodah.meeting.entities.user;

import org.abodah.meeting.entities.Base;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "MT_USR")
@SequenceGenerator(name = "seqMtUser", sequenceName = "seqMtUser", initialValue = 1, allocationSize = 1)
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMtUser")
    private Long id;
    @NotNull
    private String mail;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
    @NotNull
    private String salt;
}
