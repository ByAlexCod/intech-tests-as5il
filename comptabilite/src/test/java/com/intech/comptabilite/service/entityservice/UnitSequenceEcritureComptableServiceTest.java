package com.intech.comptabilite.service.entityservice;
import com.intech.comptabilite.model.SequenceEcritureComptable;
import com.intech.comptabilite.model.SequenceId;
import com.intech.comptabilite.repositories.SequenceEcritureComptableRepository;
import com.intech.comptabilite.service.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@SpringBootTest
public class UnitSequenceEcritureComptableServiceTest
{

    @Autowired SequenceEcritureComptableRepository sequenceEcritureComptableRepository;
    @MockBean SequenceEcritureComptableRepository mockedSequenceRepo;


    @Autowired SequenceEcritureComptableService sequenceEcritureComptableService;



    @Test
    void testUnitGetByDerniereSequenceByAnneAndCode() throws NotFoundException
    {

        SequenceEcritureComptable testedSequence = new SequenceEcritureComptable();
        testedSequence.setDerniereValeur(1);
        testedSequence.setJournalCode("ABC");
        testedSequence.setAnnee(2001);

        Mockito.when(
                mockedSequenceRepo.findById(Mockito.any())
        ).thenReturn(
                Optional.empty()
        );

        Assertions.assertThrows(NotFoundException.class, () -> {
            this.sequenceEcritureComptableService.getDernierValeurByCodeAndAnnee("ABC", 2001);
        });

        Mockito.when(
                mockedSequenceRepo.findById(Mockito.any())
        ).thenReturn(
                Optional.of(testedSequence)
        );

        Integer lastValue = this.sequenceEcritureComptableService.getDernierValeurByCodeAndAnnee(testedSequence.getJournalCode(), testedSequence.getAnnee());
        Assertions.assertEquals(1, lastValue);

    }
}
