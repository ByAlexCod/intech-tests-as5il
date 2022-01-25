package jardin;
import jardin.flore.Ail;
import jardin.flore.Etat;
import jardin.flore.IRacePure;
import jardin.flore.Vegetal;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
class VegetalTest
{
    @Test
    void testGrandir() {
        Vegetal vegetalInstance = new VegetalTestClass();
        Etat etatBase = vegetalInstance.getEtat();
        vegetalInstance.grandir();

        assertEquals(etatBase.ordinal() + 1, vegetalInstance.getEtat().ordinal());

    }
}
