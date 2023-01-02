package org.abodah.meeting.dao;

import org.abodah.meeting.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
