package modele;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testChargementDonnees {



    @Test
    public void testTransformer() throws IOException{
        FormatDonneeBrut formatDonneeBrut = mockData.get(0);
        Iris iris = new Iris(5.1, 3.5, 1.4, 0.2, Fleur.SETOSA);

        Iris irisActuel = ChargementDonnees.transformer(formatDonneeBrut);

        assertEquals(expectedIris.getSepalLength(), actualIris.getSepalLength());
        assertEquals(expectedIris.getSepalWidth(), actualIris.getSepalWidth());
        assertEquals(expectedIris.getPetalLength(), actualIris.getPetalLength());
        assertEquals(expectedIris.getPetalWidth(), actualIris.getPetalWidth());
        assertEquals(expectedIris.getVariety(), actualIris.getVariety());
    }
}