package com.example.UserManagement.controllers;

import com.example.UserManagement.model.Machine;
import com.example.UserManagement.model.MachineStatus;
import com.example.UserManagement.model.Permissions;
import com.example.UserManagement.model.User;
import com.example.UserManagement.requests.ScheduleRequest;
import com.example.UserManagement.services.MachineService;
import com.example.UserManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/machines")
public class MachineRestController {

    private final UserService userService;
    private final MachineService machineService;

    @Autowired
    public MachineRestController(UserService userService, MachineService machineService) {
        this.userService = userService;
        this.machineService = machineService;
    }

    @GetMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> allRequestParams) {
        if (userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(), Permissions.CAN_SEARCH_MACHINES)) {
            //return ResponseEntity.ok(machineService.search1(allRequestParams));
            //machineService.search(userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

            return ResponseEntity.ok(machineService.search(allRequestParams));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMachine(@RequestBody Machine machine) {
        if (userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(), Permissions.CAN_CREATE_MACHINES)) {
            machine.setDateCreated(LocalDateTime.now());
            machine.setStatus(MachineStatus.STOPPED);
            machine.setActive(true);
            User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            machine.setUser(user);
            return ResponseEntity.ok(machineService.save(machine));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping(value = "/start",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startMachine(@RequestParam Long id) {
        if (userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(), Permissions.CAN_START_MACHINES)) {
            Optional<Machine> optionalMachine = machineService.findMachineById(id);
            if (optionalMachine.isPresent()) {
                Machine machine = optionalMachine.get();
                User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
                if (!user.equals(machine.getUser()))
                    return ResponseEntity.status(403).build();
                if (machine.getStatus().equals(MachineStatus.STOPPED) && machine.isActive()) {
                    machine.setStatus(MachineStatus.STARTING);
                    machineService.save(machine);
                    machineService.getMachineQueue().add(machine);
                    machineService.getMachineTimes().add(new Date());
                    return ResponseEntity.status(200).build();
                } else
                    return ResponseEntity.status(409).body("Machine already started or deleted");

            } else {
                return ResponseEntity.status(404).build();
            }
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping(value = "/stop",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> stopMachine(@RequestParam Long id) {
        if (userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(), Permissions.CAN_START_MACHINES)) {
            Optional<Machine> optionalMachine = machineService.findMachineById(id);
            if (optionalMachine.isPresent()) {
                Machine machine = optionalMachine.get();
                User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
                if (!user.equals(machine.getUser()))
                    return ResponseEntity.status(403).build();
                if (machine.getStatus().equals(MachineStatus.RUNNING) && machine.isActive()) {
                    machine.setStatus(MachineStatus.STOPPING);
                    machineService.save(machine);
                    machineService.getMachineQueue().add(machine);
                    machineService.getMachineTimes().add(new Date());
                    return ResponseEntity.status(200).build();
                } else
                    return ResponseEntity.status(409).body("Machine already started or deleted");

            } else {
                return ResponseEntity.status(404).build();
            }
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping(value = "/restart",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restartMachine(@RequestParam Long id) {
        if (userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(), Permissions.CAN_RESTART_MACHINES)) {
            Optional<Machine> optionalMachine = machineService.findMachineById(id);
            if (optionalMachine.isPresent()) {
                Machine machine = optionalMachine.get();
                User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
                if (!user.equals(machine.getUser()))
                    return ResponseEntity.status(403).build();
                if (machine.getStatus().equals(MachineStatus.RUNNING) && machine.isActive()) {
                    machine.setStatus(MachineStatus.RESTARTING_STOPPING);
                    machineService.save(machine);
                    machineService.getMachineQueue().add(machine);
                    machineService.getMachineTimes().add(new Date());
                    return ResponseEntity.status(200).build();
                } else
                    return ResponseEntity.status(409).body("Machine already started or deleted");

            } else {
                return ResponseEntity.status(404).build();
            }
        } else {
            return ResponseEntity.status(403).build();
        }

    }

    @GetMapping(value = "/destroy",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> destroyMachine(@RequestParam Long id) {
        if (userService.isAuthorised(SecurityContextHolder.getContext().getAuthentication().getName(), Permissions.CAN_DESTROY_MACHINES)) {
            Optional<Machine> optionalMachine = machineService.findMachineById(id);
            if (optionalMachine.isPresent()) {
                Machine machine = optionalMachine.get();
                User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
                if (!user.equals(machine.getUser()))
                    return ResponseEntity.status(403).build();
                if (machine.getStatus().equals(MachineStatus.STOPPED) && machine.isActive()) {
                    machine.setActive(false);
                    machineService.save(machine);
                    return ResponseEntity.status(200).build();
                } else
                    return ResponseEntity.status(409).body("Machine already started or deleted");

            } else {
                return ResponseEntity.status(404).build();
            }
        } else {
            return ResponseEntity.status(403).build();
        }
    }


    @PostMapping(value="/schedule",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> schedule(@RequestBody ScheduleRequest scheduleRequest) {
        Optional<Machine> optionalMachine = machineService.findMachineById(scheduleRequest.getId());
        if (optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if (!user.equals(machine.getUser()))
                return ResponseEntity.status(403).build();
            machineService.ScheduleCommand(machine.getId(),scheduleRequest);
            return ResponseEntity.status(200).build();

        } else {
            return ResponseEntity.status(404).build();
        }

    }
}