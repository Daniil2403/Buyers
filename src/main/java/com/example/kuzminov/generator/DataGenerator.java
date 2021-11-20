package com.example.kuzminov.generator;

import com.example.kuzminov.entity.Buyer;
import com.example.kuzminov.repository.BuyerRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(BuyerRepository buyerRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (buyerRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
//            ExampleDataGenerator<Company> companyGenerator = new ExampleDataGenerator<>(Company.class,
//                    LocalDateTime.now());
//            companyGenerator.setData(Company::setName, DataType.COMPANY_NAME);
//            List<Company> companies = companyRepository.saveAll(companyGenerator.create(5, seed));
//
//            List<Status> statuses = statusRepository
//                    .saveAll(Stream.of("Imported lead", "Not contacted", "Contacted", "Customer", "Closed (lost)")
//                            .map(Status::new).collect(Collectors.toList()));

            logger.info("... generating 50 Buyer entities...");
            ExampleDataGenerator<Buyer> buyerGenerator = new ExampleDataGenerator<>(Buyer.class,
                    LocalDateTime.now());
            buyerGenerator.setData(Buyer::setName, DataType.FIRST_NAME);
            buyerGenerator.setData(Buyer::setLastname, DataType.LAST_NAME);
            buyerGenerator.setData(Buyer::setNumber, DataType.PHONE_NUMBER);
            buyerGenerator.setData(Buyer::setBirthday, DataType.DATE_OF_BIRTH);

            // buyerGenerator.setData(Buyer::setSex, DataType.);

            Random r = new Random(seed);
            List<Buyer> buyers = buyerGenerator.create(50, seed).stream().map(buyer -> {
                return buyer;
            }).collect(Collectors.toList());

            buyerRepository.saveAll(buyers);

            logger.info("Generated demo data");
        };
    }

}
