package devops.ecom.customerservice.schedualUsers;

import devops.ecom.customerservice.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@EnableScheduling
public class SyncConfig {

    private final CustomerService customerService;

    @Scheduled(fixedDelay = 30 * 1000) // Run every half minute
    public void syncUsers() {
        System.out.println("keycloak users sync");
        customerService.syncKeycloakUsers();
    }
}
