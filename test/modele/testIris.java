package modele;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class testIris{

    private Iris iris;

    @BeforeEach
    public void setUp() {
        iris = new Iris(5.0, 3.0. 2.0, 0.5, Fleur.SETOSA);
    }

    @Test
    public void testToString() {
        String resultatVoulu = "FormatDonneeBrut{sepalLength='5.0', sepalWidth='3.0', petalLength='2.0', petalWidth='0.5', variety=SETOSA}";
        assertEquals(resultatVoulu, iris.toString());
    }
}
