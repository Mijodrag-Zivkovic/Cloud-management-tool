package com.example.UserManagement.listeners;

import com.example.UserManagement.model.Machine;
import com.example.UserManagement.model.MachineStatus;
import com.example.UserManagement.repositories.MachineRepository;
import com.example.UserManagement.services.MachineService;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;

@ApplicationScope
public class MachineListener {



//    @PreUpdate
//    public void listener(Machine machine) throws InterruptedException {
//        System.out.println("u listeneru");
//        if(machine.getStatus().equals(MachineStatus.STARTING))
//        {
//            Thread.sleep(5000);
//            machine.setStatus(MachineStatus.RUNNING);
//            System.out.println("pre save");
//        }
//    }
}
