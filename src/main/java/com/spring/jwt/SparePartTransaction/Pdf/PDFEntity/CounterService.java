package com.spring.jwt.SparePartTransaction.Pdf.PDFEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final CounterRepository counterRepository;
    @Transactional
    public long getNextCounter(String name, long initialValue) {
        Counter counter = counterRepository.findByNameForUpdate(name);
        if (counter == null) {
            counter = new Counter();
            counter.setName(name);
            counter.setValue(initialValue);
        }
        long nextValue = counter.getValue();
        counter.setValue(nextValue + 1);
        counterRepository.save(counter);
        return nextValue;
    }
}
