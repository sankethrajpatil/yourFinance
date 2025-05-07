package com.example.yourFinance.service;

import com.example.yourFinance.model.Expenditure;
import com.example.yourFinance.model.User;
import com.example.yourFinance.repository.ExpenditureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenditureService {

    private final ExpenditureRepository repo;

    public Expenditure save(Expenditure e) {
        return repo.save(e);
    }

    public List<Expenditure> findByUser(User user) {
        return repo.findByUser(user);
    }
}
