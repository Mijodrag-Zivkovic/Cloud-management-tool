package com.example.UserManagement.bootstrap;

import com.example.UserManagement.model.*;
import com.example.UserManagement.repositories.MachineRepository;
import com.example.UserManagement.repositories.PermissionRepository;
import com.example.UserManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PreStartup implements CommandLineRunner {

    private PermissionRepository permissionRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MachineRepository machineRepository;

    @Autowired
    public PreStartup(PermissionRepository permissionRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, MachineRepository machineRepository) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.machineRepository = machineRepository;
    }

    @Override
    public void run(String... args) throws Exception
    {

        List<User> users = new ArrayList<>();
        for(int i=1;i<=5;i++)
        {
            User u = new User();
            u.setName("name"+i);
            u.setSurname("surname"+i);
            u.setEmail("email"+i);
            u.setPassword("password"+i);
            u.setPassword(this.passwordEncoder.encode("password"+i));
            userRepository.save(u);
            //users.add(u);
        }
        //userRepository.saveAll(users);

        List<Permission> permissions = new ArrayList<>();
        for(Permissions p : Permissions.values())
        {
            Permission perm = new Permission();
            perm.setPermission(p);
            permissions.add(perm);
            permissionRepository.save(perm);
        }

        User user = new User();
        user.setName("probni");
        user.setSurname("probni");
        user.setEmail("probni");
        //user.setPassword("probni");
        user.setPassword(this.passwordEncoder.encode("probni"));
//        Permission permission = new Permission();
//        permission.setPermision(Permissions.CAN_READ_USERS);
        for(Permission p : permissions)
            user.getPermissions().add(p);
//        user.getPermissions().add(permissions.get(0));
//        user.getPermissions().add(permissions.get(1));
//        user.getPermissions().add(permissions.get(2));
//        user.getPermissions().add(permissions.get(3));
        //user.getMachines().add(machine);
        userRepository.save(user);

        Machine machine = new Machine();
        machine.setActive(true);
        machine.setStatus(MachineStatus.RUNNING);
        machine.setDateCreated(LocalDateTime.now());
        machine.setName("Machine 1");
        machine.setUser(user);
        machineRepository.save(machine);

        Machine machine2 = new Machine();
        machine2.setActive(true);
        machine2.setStatus(MachineStatus.RUNNING);
        machine2.setDateCreated(LocalDateTime.of(2021,12,21,12,12,12));
        machine2.setName("Machine 2");
        machine2.setUser(user);
        machineRepository.save(machine2);
        System.out.println("Loaded!");


    }

}
