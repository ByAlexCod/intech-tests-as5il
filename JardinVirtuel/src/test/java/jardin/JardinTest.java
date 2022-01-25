package jardin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.AbstractMap.SimpleEntry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jardin.flore.Ail;
import jardin.flore.Betterave;
import jardin.flore.Etat;

@ExtendWith(MockitoExtension.class)
public class JardinTest {
	
	@Mock
	Betterave b;

	private Jardin j;

	@BeforeEach
	private void init() {
		j = new Jardin(5, 5);
	}

	@Test
	public void ajouterPanierVide() {

		// Arrange

		// Act
		j.ajouterPanier("Ail", 3);

		// Assert
		Assertions.assertEquals(3, j.getPanier().get("Ail"));
	}

	@Test
	public void ajouterPanierNonVide() {

		// Arrange

		j.getPanier().put("Ail", 5);

		// Act
		j.ajouterPanier("Ail", 3);

		// Assert
		Assertions.assertEquals(8, j.getPanier().get("Ail"));
	}

	@Test
	public void testPasserSaisonSuivanteNonMort() {
		// Arrange

		j.getEmplacement()[0][0] = new Emplacement(new Ail());

		// Act
		j.passerSaisonSuivante();
		Etat result = j.getEmplacement()[0][0].getVeg().getEtat();

		// Assert
		assertEquals(Etat.GERME, result);
	}

	@Test
	public void testPasserSaisonSuivanteMort() {
		// Arrange

		j.getEmplacement()[0][0] = new Emplacement(new Ail());

		// Act
		for (int i = 0; i < 6; i++) {
			j.passerSaisonSuivante();
		}

		// Assert
		assertNull(j.getEmplacement()[0][0]);
	}

	@Test
	public void testRecolterPasEnFleur() {
		// Arrange
		j.getEmplacement()[0][0] = new Emplacement(new Ail());

		// Act
		j.recolter();

		// Assert
		assertNull(j.getPanier().get("Ail"));
	}

	@Test
	public void testRecolterEnFleurRacePure() {
		// Arrange
		j.getEmplacement()[0][0] = new Emplacement(new Ail());

		// Act
		for (int i = 0; i < 4; i++) {
			j.passerSaisonSuivante();
		}
		j.recolter();

		// Assert
		assertEquals(3, j.getPanier().get("Ail"));
	}

	@Test
	public void testRecolterEnFleurOgm() {
		// Arrange
		Betterave b = new Betterave();
		j.getEmplacement()[0][0] = new Emplacement(b);

		// Act
		for (int i = 0; i < 4; i++) {
			j.passerSaisonSuivante();
		}
		j.recolter();

		// Assert
		boolean found = false;
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				Emplacement e = j.getEmplacement()[x][y];
				if (e != null && e.getVeg().equals(b)) {
					found = true;
				}
			}
		}
		assertTrue(found);
		assertNull(j.getEmplacement()[0][0]);
	}
	
	@Test
	public void testRecolterEnFleurOgmAvecMock() {
		// Arrange
		SimpleEntry<Integer, Integer> coords = new SimpleEntry<>(1,1);
		when(b.getEtat()).thenReturn(Etat.FLEUR, Etat.GRAINE);
		when(b.seDupliquer(5, 5)).thenReturn(coords);
		
		j.getEmplacement()[0][0] = new Emplacement(b);

		// Act
		j.recolter();

		// Assert
		verify(b, times(2)).getEtat();
		verify(b).seDupliquer(5, 5);
		
		assertNotNull(j.getEmplacement()[1][1]);
		assertNull(j.getEmplacement()[0][0]);
	}

}
