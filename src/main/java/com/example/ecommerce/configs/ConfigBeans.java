package com.example.ecommerce.configs;


import com.example.ecommerce.dto.TransactionService;
import com.example.ecommerce.utils.Utilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ConfigBeans {

    @Bean
    Utilities getUtilities() {
        return new Utilities();
    }

    @Bean
    @RequestScope
    TransactionService getTransactionService() {
        return new TransactionService();
    }

    @Bean
    @RequestScope
    DistributedTransactions getDistributedTransactions() {
        return new DistributedTransactions();
    }

}
