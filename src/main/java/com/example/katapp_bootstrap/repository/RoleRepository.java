package com.example.katapp_bootstrap.repository;

import com.example.katapp_bootstrap.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Modifying
    @Query("delete from Role r where r.id=:id")
    void deleteRole(Long id);
}
