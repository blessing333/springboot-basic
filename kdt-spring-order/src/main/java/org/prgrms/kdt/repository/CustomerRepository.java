package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.customer.CustomerProperties;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomerRepository{
    public final CustomerProperties properties;

    public CustomerRepository(CustomerProperties properties) {
        this.properties = properties;
    }

    public List<Customer> findBlackList() {
        List<Customer> blackList = new ArrayList<>();
        String filePath = new StringBuilder()
                                .append(properties.getDirectory())
                                .append(properties.getFileName())
                                .append(properties.getExtension())
                                .toString();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                blackList.add(getCustomerFrom(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return blackList;
    }

    private Customer getCustomerFrom(String line){
        String[] elements = line.split(",");
        return new Customer(UUID.fromString(elements[0]), elements[1], true);
    }
}
