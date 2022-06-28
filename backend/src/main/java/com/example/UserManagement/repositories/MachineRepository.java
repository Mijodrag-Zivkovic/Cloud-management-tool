package com.example.UserManagement.repositories;

import com.example.UserManagement.model.Machine;
import com.example.UserManagement.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MachineRepository extends CrudRepository<Machine,Long> {



    //@Query("SELECT m FROM Machine m WHERE m.user = ?1 ")
    List<Machine> findByUser_Id(Long id);




}
