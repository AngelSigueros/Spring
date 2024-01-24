package com.certidevs;

import org.springframework.stereotype.Component;



@Component
public class UserRegistration {

    private final NotificationService notificationService;


    /*
        Esta es otra forma

    @Autowired
    private final NotificationService notificationService;
    */

    /*
        Con constructor
    */
    public UserRegistration(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void holaSpring() {

        notificationService.holaMundo();

    }
}
