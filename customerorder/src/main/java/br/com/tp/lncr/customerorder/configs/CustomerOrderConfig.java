package br.com.tp.lncr.customerorder.configs;

import br.com.tp.lncr.core.adapters.customerorder.CustomerOrderControllerImpl;
import br.com.tp.lncr.core.adapters.customerorder.CustomerOrderMapper;
import br.com.tp.lncr.core.interfaces.customerorder.CustomerOrderController;
import br.com.tp.lncr.core.interfaces.customerorder.CustomerOrderDatabase;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lncr.customer-order")
public class CustomerOrderConfig {

    private String locationPrefix;

    public String getLocationPrefix() {
        return locationPrefix;
    }

    public void setLocationPrefix(String locationPrefix) {
        this.locationPrefix = locationPrefix;
    }

    @Bean
    public CustomerOrderController customerOrderController(CustomerOrderDatabase customerOrderDatabase, CustomerOrderMapper customerOrderMapper){
        return new CustomerOrderControllerImpl(customerOrderDatabase);
    }

    @Bean
    public CustomerOrderMapper customerOrderMapper() {
        return new CustomerOrderMapper();
    }



}
