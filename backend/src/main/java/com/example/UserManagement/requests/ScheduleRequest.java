package com.example.UserManagement.requests;

import com.example.UserManagement.model.MachineCommands;
import com.example.UserManagement.model.MachineStatus;
import lombok.Data;

@Data
public class ScheduleRequest {

    Long id;
    MachineCommands machineCommands;
    int second;
    int minutes;
    int hour;
    int day;
    int month;
}
