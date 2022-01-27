package com.intech.comptabilite.service.entityservice;
import com.intech.comptabilite.model.CompteComptable;
import com.intech.comptabilite.model.JournalComptable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.fail;
@SpringBootTest
public class CompteComptableServiceTest
{

    @Autowired
    CompteComptableService compteComptableService;



    @Test
    void testUnitCompteComptableGetByCode() {
        List<CompteComptable> comptaComptables = new ArrayList<>();
        Random random = new Random();
        int randomJournauxListSize = random.nextInt(75);
        int indexChosenToTest = random.nextInt(randomJournauxListSize);
        Integer randomCodeToTest = null;
        for (int i = 0; i < randomJournauxListSize; i++) {

            Integer randomCode = random.nextInt(5000);
            if(i == indexChosenToTest) {
                randomCodeToTest = randomCode;
            }
            comptaComptables.add(new CompteComptable(randomCode, "Nom de test"));
        }
        if(randomCodeToTest == null) {
            fail("Le comportement aléatoire a crashé");
        }

        CompteComptable resultJournal = compteComptableService.getByNumero(comptaComptables, randomCodeToTest);
        Assertions.assertNotNull(resultJournal);
        Assertions.assertEquals(randomCodeToTest, resultJournal.getNumero());
    }
}
