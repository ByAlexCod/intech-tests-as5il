package com.intech.comptabilite.service.entityservice;
import com.intech.comptabilite.model.JournalComptable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.fail;
@SpringBootTest
public class JournalComptableServiceTest
{

    @Autowired
    JournalComptableService journalComptableService;

    String randomCode(int size) {
        String possibilities = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random randomNumber = new Random();
        StringBuilder finalized = new StringBuilder();
        for (int i = 0; i < size - 1; i++) {
            //4
            int randomNo = randomNumber.nextInt(possibilities.length());

            //5
            char character = possibilities.charAt(randomNo);

            //6
            finalized.append(Character.toUpperCase(character));
        }

        return  finalized.toString();
    }

    @Test
    void testUnitJournalComptableGetByCode() {
        List<JournalComptable> journaux = new ArrayList<>();
        Random random = new Random();
        int randomJournauxListSize = random.nextInt(75);
        int indexChosenToTest = random.nextInt(randomJournauxListSize);
        String randomCodeToTest = null;
        for (int i = 0; i < randomJournauxListSize; i++) {

            String randomCode = randomCode(random.nextInt(5));
            if(i == indexChosenToTest) {
                randomCodeToTest = randomCode;
            }
            journaux.add(new JournalComptable(randomCode, "Nom de test"));
        }
        if(randomCodeToTest == null) {
            fail("Le comportement aléatoire a crashé");
        }

        JournalComptable resultJournal = journalComptableService.getByCode(journaux, randomCodeToTest);
        Assertions.assertNotNull(resultJournal);
        Assertions.assertEquals(randomCodeToTest, resultJournal.getCode());
    }
}
