package com.intech.comptabilite.service.entityservice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import com.intech.comptabilite.model.*;
import com.intech.comptabilite.repositories.EcritureComptableRepository;
import com.intech.comptabilite.service.exceptions.NotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public class EcritureComptableServiceTest {

    @MockBean EcritureComptableRepository ecritureComptableRepository;

	
	@Autowired
	private EcritureComptableService ecritureComptableService;

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        Assertions.assertTrue(ecritureComptableService.isEquilibree(vEcriture));

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assertions.assertFalse(ecritureComptableService.isEquilibree(vEcriture));
    }

    @Test
    void testGetDebit() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));

        BigDecimal expected = new BigDecimal(341);
        expected = expected.setScale(2, RoundingMode.CEILING);

        Assertions.assertEquals(expected, ecritureComptableService.getTotalDebit(vEcriture));
    }

    @Test
    void testGetCredit() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));

        BigDecimal expected = new BigDecimal(341);
        expected = expected.setScale(2, RoundingMode.CEILING);

        Assertions.assertEquals(expected, ecritureComptableService.getTotalCredit(vEcriture));
    }

    CompteComptable saveAndGetRandomCompteComptable() {
        CompteComptable compteComptable = new CompteComptable();
        compteComptable.setLibelle("Compte comptable 1");
        compteComptable.setNumero((new Random()).nextInt(1000));
        return compteComptable;
    }

    @Test
    void testGetByRef() throws NotFoundException
    {

        Mockito.when(ecritureComptableRepository.getByReference("BQ-2016/00001"))
                .thenReturn(java.util.Optional.of(new EcritureComptable()), Optional.empty());

        try {
            EcritureComptable e = ecritureComptableService.getEcritureComptableByRef("BQ-2016/00001");
            Assertions.assertNotNull(e);
        } catch (Exception e) {
            Assertions.fail("Ecriture comptable should be found");
        }

        try {
            EcritureComptable e = ecritureComptableService.getEcritureComptableByRef("BQ-2016/00001");
            Assertions.fail("Ecriture comptable should not be found");
        } catch (Exception ignored) {

        }

        //        String REFERENCE = "BQ-2016/00001";
        //        CompteComptable compteComptable = saveAndGetRandomCompteComptable();
        //
        //
        //        JournalComptable journalComptable;
        //        Integer vEcritureId = (new Random()).nextInt(1000);
        //        EcritureComptable vEcriture;
        //        journalComptable = new JournalComptable();
        //        journalComptable.setCode("ABCD");
        //        journalComptable.setLibelle("Journal de test");
        //        vEcriture = new EcritureComptable();
        //
        //        vEcriture.setId(vEcritureId);
        //        vEcriture.setLibelle("Equilibrée");
        //        vEcriture.setDate(new Date());
        //        vEcriture.setJournal(journalComptable);
        //
        //
        //        LigneEcritureComptable ligneEcritureComptableA = this.createLigne(1, "200.50", null);
        //        ligneEcritureComptableA.setLigneId(new LigneId(vEcritureId, (new Random()).nextInt(1000)));
        //        ligneEcritureComptableA.setCompteComptable(compteComptable);
        //
        //        LigneEcritureComptable ligneEcritureComptableB = this.createLigne(2, "100.50", "33");
        //        ligneEcritureComptableB.setLigneId(new LigneId(vEcritureId, (new Random()).nextInt(1000)));
        //        ligneEcritureComptableB.setCompteComptable(compteComptable);
        //
        //
        //        vEcriture.getListLigneEcriture().add(ligneEcritureComptableA);
        //        vEcriture.getListLigneEcriture().add(ligneEcritureComptableB);
        //
        //
        //        vEcriture.setReference(REFERENCE);
        //
        //
        //        String referenceGiven = vEcriture.getReference();
        //        Assertions.assertNotNull(referenceGiven);
        //        Assertions.assertEquals(REFERENCE, referenceGiven);
        //
        //        ecritureComptableService.insertEcritureComptable(vEcriture);
        //        EcritureComptable foundEcriture = ecritureComptableService.getEcritureComptableByRef(REFERENCE);
        //        Assertions.assertEquals(REFERENCE, foundEcriture.getReference());
    }

    @Test
    void updateEcritureComptableTest() {

    }

}
