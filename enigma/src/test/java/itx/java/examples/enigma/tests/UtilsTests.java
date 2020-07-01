package itx.java.examples.enigma.tests;

import itx.java.examples.enigma.Utils;
import itx.java.examples.enigma.configuration.EnigmaConfiguration;
import itx.java.examples.enigma.configuration.EnigmaSettings;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gergej on 25.1.2017.
 */
public class UtilsTests {

    @Test
    public void readEnigmaConfigutationTest() throws IOException {
        InputStream is = UtilsTests.class.getClassLoader().getResourceAsStream("configurations/enigma-test-configuration-26-01.json");
        Assert.assertNotNull(is);
        EnigmaConfiguration enigmaConfiguration = Utils.readEnigmaConfiguration(is);
        Assert.assertNotNull(enigmaConfiguration);
        Assert.assertNotNull(enigmaConfiguration.getAplhabet());
        Assert.assertNotNull(enigmaConfiguration.getRotorParameters());
        Assert.assertTrue(enigmaConfiguration.getRotorParameters().size() > 0);
        Assert.assertNotNull(enigmaConfiguration.getEnigmaSetup());
        Assert.assertNotNull(enigmaConfiguration.getEnigmaSetup().getPlugBoardSetup());
        Assert.assertNotNull(enigmaConfiguration.getEnigmaSetup().getRotorOrdinals());
        Assert.assertTrue(enigmaConfiguration.getEnigmaSetup().getRotorOrdinals().size() > 0);
        Assert.assertNotNull(enigmaConfiguration.getEnigmaSetup().getRotorStartingPositions());
        Assert.assertTrue(enigmaConfiguration.getEnigmaSetup().getRotorStartingPositions().size() > 0);
    }

    @Test
    public void readEnigmaSetupTest() throws IOException {
        InputStream is = UtilsTests.class.getClassLoader().getResourceAsStream("configurations/enigma-test-setup-26-01.json");
        Assert.assertNotNull(is);
        EnigmaSettings enigmaSettings = Utils.readEnigmaSetup(is);
        Assert.assertNotNull(enigmaSettings);
        Assert.assertNotNull(enigmaSettings.getPlugBoardSetup());
        Assert.assertNotNull(enigmaSettings.getRotorOrdinals());
        Assert.assertTrue(enigmaSettings.getRotorOrdinals().size() > 0);
        Assert.assertNotNull(enigmaSettings.getRotorStartingPositions());
        Assert.assertTrue(enigmaSettings.getRotorStartingPositions().size() > 0);
    }

}
