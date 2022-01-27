package com.intech.comptabilite.service.entityservice;
import com.intech.comptabilite.model.SequenceEcritureComptable;
import com.intech.comptabilite.model.SequenceId;
import com.intech.comptabilite.repositories.SequenceEcritureComptableRepository;
import com.intech.comptabilite.service.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
@SpringBootTest
public class SequenceEcritureComptableServiceTest
{

    @Autowired SequenceEcritureComptableRepository sequenceEcritureComptableRepository;


    @Autowired SequenceEcritureComptableService sequenceEcritureComptableService;

    @Test
    void testUpsertInsert() {
        SequenceEcritureComptable baseSequence = new SequenceEcritureComptable();
        baseSequence.setAnnee(2022);
        baseSequence.setDerniereValeur(1);
        baseSequence.setJournalCode("AC");
        Assertions.assertDoesNotThrow(() -> {
            sequenceEcritureComptableService.upsert(baseSequence);
        });
        List<SequenceEcritureComptable> allSequences = new ArrayList<>();
        sequenceEcritureComptableRepository.findAll().forEach(allSequences::add);

        int countGood = 0;

        for (var sequence: allSequences) {
            if(sequence.getAnnee() == 2022 && sequence.getDerniereValeur() == 1 && sequence
                    .getJournalCode().equals("AC")) {
                countGood ++;
            }
        }
        Assertions.assertEquals(1, countGood);
    }

    @Test
    void testUpsertUpdate() {
        SequenceEcritureComptable baseSequence = new SequenceEcritureComptable();
        baseSequence.setAnnee(2022);
        baseSequence.setDerniereValeur(1);
        baseSequence.setJournalCode("AC");
        Assertions.assertDoesNotThrow(() -> {
            sequenceEcritureComptableService.upsert(baseSequence);
        });

        baseSequence.setDerniereValeur(2);
        Assertions.assertDoesNotThrow(() -> {
            sequenceEcritureComptableService.upsert(baseSequence);
        });

        List<SequenceEcritureComptable> allSequences = new ArrayList<>();
        sequenceEcritureComptableRepository.findAll().forEach(allSequences::add);

        int countGood = 0;

        for (var sequence: allSequences) {
            if(sequence.getAnnee() == 2022 && sequence.getDerniereValeur() == 2 && sequence
                    .getJournalCode().equals("AC")) {
                countGood ++;
            }
        }
        Assertions.assertEquals(1, countGood);
    }


}
