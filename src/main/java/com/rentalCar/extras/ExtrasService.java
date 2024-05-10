package com.rentalCar.extras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExtrasService {

    private final ExtrasRepository extrasRepository;

    @Autowired
    public ExtrasService(ExtrasRepository extrasRepository) {
        this.extrasRepository = extrasRepository;
    }

    public List<Extras> getExtras(){
       return this.extrasRepository.findAll();
    }

    public Optional<Extras> getExtraById(Long id){
        return this.extrasRepository.findById(id);
    }

    public Extras createExtra(Extras extras){
        return this.extrasRepository.save(extras);
    }

    public Extras editExtra(Extras extras){
        return this.extrasRepository.save(extras);
    }

}
