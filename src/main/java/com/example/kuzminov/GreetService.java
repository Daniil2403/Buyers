package com.example.kuzminov;

import org.springframework.stereotype.Service;
import com.example.kuzminov.entity.Buyer;
import com.example.kuzminov.repository.BuyerRepository;

import java.util.List;

@Service 
public class GreetService {

    private final BuyerRepository buyerRepository;


    public GreetService(BuyerRepository buyerRepository) { 
        this.buyerRepository = buyerRepository;
    }

    public List<Buyer> findAllBuyers() {
        return buyerRepository.findAll();
    }

    public long countBuyers() {
        return buyerRepository.count();
    }

    public void deleteBuyer(Buyer buyer) {
    	buyerRepository.delete(buyer);
    }

    public void saveBuyer(Buyer buyer) {
        if (buyer == null) { 
            System.err.println("Buyer is null. Are you sure you have connected your form to the application?");
            return;
        }
        buyerRepository.save(buyer);
    }
}