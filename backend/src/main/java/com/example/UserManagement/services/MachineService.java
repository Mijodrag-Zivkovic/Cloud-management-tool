package com.example.UserManagement.services;

import com.example.UserManagement.model.Machine;
import com.example.UserManagement.model.MachineCommands;
import com.example.UserManagement.model.MachineStatus;
import com.example.UserManagement.model.User;
import com.example.UserManagement.repositories.MachineRepository;
import com.example.UserManagement.requests.ScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.*;

@Service
public class MachineService {
    private MachineRepository machineRepository;
    @PersistenceUnit
    private EntityManagerFactory emf;
    private TaskScheduler taskScheduler;

    private ArrayList<Machine> machineQueue;
    private ArrayList<Date> machineTimes;

    @Autowired
    public MachineService(MachineRepository machineRepository, TaskScheduler taskScheduler)
    {
        this.machineRepository = machineRepository;
        this.taskScheduler = taskScheduler;
        machineQueue = new ArrayList<>();
        machineTimes = new ArrayList<>();
    }

    public Machine save(Machine machine){
        return machineRepository.save(machine);
    }


    public List<Machine> search1(User u){
        System.out.println(u.getEmail());
        System.out.println(u.getId());
        return machineRepository.findByUser_Id(u.getId());
    }

    ////////////////////////////////////////////////////////////////////////////
    public List<Machine> search(Map<String, String> allRequestParams){
        EntityManager em = emf.createEntityManager();
        //String customQuery = "SELECT m FROM Machine m where m.dateCreated>'2021-12-22' ";
        String customQuery = "SELECT m FROM Machine m where ";
        boolean flag = false;
        String helper = "";
        for (Map.Entry<String,String> entry : allRequestParams.entrySet())
        {
            System.out.println("u foru");
            if(entry.getKey().equals("name"))
            {
                //System.out.println(entry.getKey() + " " + entry.getValue());
                customQuery=customQuery+"m.name=\'"+entry.getValue() +"\' and ";
//                System.out.println(customQuery);

            }
            else if(entry.getKey().equals("status"))
            {
                customQuery=customQuery+"m.status=\'"+entry.getValue() +"\' and ";
            }
            else if(entry.getKey().equals("dateFrom"))
            {
                helper=helper+"m.dateCreated>\'"+entry.getValue() +"\' and ";
                flag = true;
            }
            else if(entry.getKey().equals("dateTo") && flag)
            {
                customQuery+=helper;
                customQuery=customQuery+" m.dateCreated<\'" +entry.getValue() + "\'";
            }

        }
        System.out.println(customQuery);
        if(customQuery.endsWith("and "))
        {
            customQuery=customQuery.substring(0,customQuery.length()-4);
        }
        else if(customQuery.endsWith("where "))
            customQuery=customQuery.substring(0,customQuery.length()-6);
        System.out.println(customQuery);
        //String customQuery2 = "SELECT * FROM Machine ";

        List<Machine> testValues = em.createQuery(customQuery).getResultList();
        return  testValues;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////
    public Optional<Machine> findMachineById(Long id){
        return machineRepository.findById(id);
    }


    public List<Machine> getAll()
    {
        return (List<Machine>) machineRepository.findAll();
    }

    public ArrayList<Machine> getMachineQueue() {
        return machineQueue;
    }

    public ArrayList<Date> getMachineTimes() {
        return machineTimes;
    }

    @Scheduled(fixedDelay = 5000)
    public void clearMachineQueue(){
        System.out.println("schedule " + machineQueue.size());
        Iterator<Machine> iterator = machineQueue.iterator();
        Date currentDate = new Date();
        int index = 0;
        while(iterator.hasNext())
        {
            Machine machine = iterator.next();
            System.out.println(machine.getStatus());
            System.out.println(currentDate.getTime() - machineTimes.get(index).getTime());
            double timeDiff = ((double)(currentDate.getTime() - machineTimes.get(index).getTime()))/1000;
            if(machine.getStatus().equals(MachineStatus.STARTING) && timeDiff>10)
            {
                System.out.println("running");
                machine.setStatus(MachineStatus.RUNNING);
                machineRepository.save(machine);
                iterator.remove();
                machineTimes.remove(index);
                index--;
            }
            else if(machine.getStatus().equals(MachineStatus.STOPPING) && timeDiff>10)
            {
                System.out.println("stopped");
                machine.setStatus(MachineStatus.STOPPED);
                machineRepository.save(machine);
                iterator.remove();
                machineTimes.remove(index);
                index--;
            }
            else if(machine.getStatus().equals(MachineStatus.RESTARTING_STOPPING) && timeDiff>5)
            {
                System.out.println("restart stopping machine");
                machine.setStatus(MachineStatus.RESTARTING_STARTING);
                machineRepository.save(machine);
                //iterator.remove();
                //machineTimes.remove(index);
                //index--;
            }
            else if(machine.getStatus().equals(MachineStatus.RESTARTING_STARTING) && timeDiff>10)
            {
                System.out.println("restart starting machine");
                machine.setStatus(MachineStatus.RUNNING);
                machineRepository.save(machine);
                iterator.remove();
                machineTimes.remove(index);
                index--;
            }
//            iterator.remove();
//            machineTimes.remove(index);
            index++;
        }
        System.out.println("kraj");
    }

    public void ScheduleCommand(Long machineId, ScheduleRequest scheduleRequest)
    {
        //+ scheduleRequest.getSecond()+ " "
        String expression = "" + scheduleRequest.getSecond() + " " + scheduleRequest.getMinutes() + " "
                + scheduleRequest.getHour() + " " + scheduleRequest.getDay() + " " + scheduleRequest.getMonth() + " *";
        System.out.println(expression);
        CronTrigger cronTrigger = new CronTrigger(expression); // "0 0 0 25 * *"
        this.taskScheduler.schedule(() -> {
//            System.out.println("Getting salary...");
            Optional<Machine> optionalMachine = this.findMachineById(machineId);
            if(optionalMachine.isPresent())
            {
                Machine machine = optionalMachine.get();
                if(scheduleRequest.getMachineCommands().equals(MachineCommands.START) && machine.getStatus().equals(MachineStatus.STOPPED))
                {
                    machine.setStatus(MachineStatus.STARTING);
                    this.save(machine);
                    this.getMachineQueue().add(machine);
                    this.getMachineTimes().add(new Date());
                }
                else if(scheduleRequest.getMachineCommands().equals(MachineCommands.STOP) && machine.getStatus().equals(MachineStatus.RUNNING))
                {
                    machine.setStatus(MachineStatus.STOPPING);
                    this.save(machine);
                    this.getMachineQueue().add(machine);
                    this.getMachineTimes().add(new Date());
                }
                else if(scheduleRequest.getMachineCommands().equals(MachineCommands.RESTART) && machine.getStatus().equals(MachineStatus.RUNNING))
                {
                    machine.setStatus(MachineStatus.RESTARTING_STOPPING) ;
                    this.save(machine);
                    this.getMachineQueue().add(machine);
                    this.getMachineTimes().add(new Date());
                }
            }


        }, cronTrigger);
    }
//    public User hire(String username, Integer salary) {
//        User user = this.userRepository.findByUsername(username);
//        user.setSalary(salary);
//        this.userRepository.save(user);
//
//        CronTrigger cronTrigger = new CronTrigger("0 * * * * *"); // "0 0 0 25 * *"
//        this.taskScheduler.schedule(() -> {
//            System.out.println("Getting salary...");
//            this.userRepository.increaseBalance(salary);
//        }, cronTrigger);
//
//        return user;
//    }

}
