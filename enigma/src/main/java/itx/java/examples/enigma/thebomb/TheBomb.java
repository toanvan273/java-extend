package itx.java.examples.enigma.thebomb;

import itx.java.examples.enigma.Enigma;
import itx.java.examples.enigma.configuration.EnigmaConfiguration;
import itx.java.examples.enigma.configuration.EnigmaSettings;
import org.apache.commons.collections4.iterators.PermutationIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gergej on 24.1.2017.
 */
public class TheBomb {

    final private static Logger LOG = LoggerFactory.getLogger(TheBomb.class);

    private EnigmaConfiguration enigmaConfiguration;
    private String encodedMessage;
    private String expectedString;
    private int rotorCount;
    private Character[][] plugBoardSetup;

    public TheBomb(EnigmaConfiguration enigmaConfiguration, String encodedMessage, String expectedString, Integer rotorCount, Character[][] plugBoardSetup) {
        LOG.info("init ...");
        this.enigmaConfiguration = enigmaConfiguration;
        this.encodedMessage = encodedMessage;
        this.expectedString = expectedString;
        this.rotorCount = rotorCount;
        this.plugBoardSetup = plugBoardSetup;
    }

    public EnigmaConfiguration findEnigmaSetup() throws Exception {
        LOG.info("searching for enigma setup ...");
        long duration = System.currentTimeMillis();

        List<List<Integer>> rotorGroups = new ArrayList<>();
        int groupCount = (enigmaConfiguration.getRotorParameters().size() - rotorCount) + 1;
        for (int i=0; i<groupCount; i++) {
            List<Integer> rotorGorup = new ArrayList<>();
            for (int j=0; j<rotorCount; j++) {
                rotorGorup.add(new Integer(i + j));
            }
            rotorGroups.add(rotorGorup);
        }

        //TODO: implement iteration through all plugboard setup possibilities
        EnigmaSettings enigmaSettings = new EnigmaSettings(null, null, plugBoardSetup);
        EnigmaConfiguration result = new EnigmaConfiguration(enigmaConfiguration.getAplhabet(), enigmaConfiguration.getRotorParameters(), enigmaSettings);

        for (int i=0; i<rotorGroups.size(); i++) {
            //iterate through all rotor group posiibilities
            PermutationIterator<Integer> pe = new PermutationIterator<>(rotorGroups.get(i));
            while (pe.hasNext()) {
                //iterate through all rotor order possibilities
                List<Integer> rotorOrdinals = pe.next();
                RotorGroupIterator rotorGroupIterator = new RotorGroupIterator(rotorOrdinals.size(), enigmaConfiguration.getAplhabet());
                while (rotorGroupIterator.hasNext()) {
                    //iterate through all rotor starting positions
                    List<Character> rotorStartingPositions = rotorGroupIterator.getNext();
                    enigmaSettings.setRotorOrdinals(rotorOrdinals);
                    enigmaSettings.setRotorStartingPositions(rotorStartingPositions);
                    printSetup(rotorOrdinals, rotorStartingPositions);
                    if (testEnigmaSetup(result, encodedMessage, expectedString)) {
                        duration = System.currentTimeMillis() - duration;
                        LOG.info("done: duration = " + duration + " ms");
                        return result;
                    }
                }
            }
        }

        duration = System.currentTimeMillis() - duration;
        LOG.info("done: duration = " + duration + " ms");
        throw new Exception("Enigma setting not found !");
    }

    private boolean testEnigmaSetup(EnigmaConfiguration enigmaConfiguration, String encodedMessage, String expectedString) {
        Enigma enigma = Enigma.builder().fromConfiguration(enigmaConfiguration).build();
        for (int i=0; i<expectedString.length(); i++) {
            Character character = enigma.encryptOrDecrypt(encodedMessage.charAt(i));
            Character expectedCharacter = expectedString.charAt(i);
            if (!character.equals(expectedCharacter)) {
                return false;
            }
        }
        return true;
    }

    private void printSetup(List<Integer> rotorOrdinals, List<Character> rotorStartingPositions) {
        LOG.info("Testing: " + rotorOrdinals + " " + rotorStartingPositions );
    }

}
