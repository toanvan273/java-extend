package itx.java.examples.enigma.impl.enigma;

import itx.java.examples.enigma.Enigma;
import itx.java.examples.enigma.Utils;
import itx.java.examples.enigma.alphabet.Alphabet;
import itx.java.examples.enigma.configuration.EnigmaConfiguration;
import itx.java.examples.enigma.impl.rotors.RotorGroupBuilder;
import itx.java.examples.enigma.plugboard.PlugBoard;
import itx.java.examples.enigma.rotors.Reflector;
import itx.java.examples.enigma.rotors.Rotor;
import itx.java.examples.enigma.rotors.RotorGroup;

/**
 * Created by gergej on 17.1.2017.
 */
public class EnigmaBuilder {

    private Alphabet alphabet;
    private Reflector reflector;
    private RotorGroup rotorGroup;
    private PlugBoard plugBoard;

    public EnigmaBuilder setAlphabet(Alphabet alphabet) {
        this.alphabet = alphabet;
        return this;
    }

    public EnigmaBuilder setReflector(Reflector reflector) {
        this.reflector = reflector;
        return this;
    }

    public EnigmaBuilder setRotorGroup(RotorGroup rotorGroup) {
        this.rotorGroup = rotorGroup;
        return this;
    }

    public EnigmaBuilder setPlugBoard(PlugBoard plugBoard) {
        this.plugBoard = plugBoard;
        return this;
    }

    public EnigmaBuilder fromConfiguration(EnigmaConfiguration enigmaConfiguration) {
        this.alphabet = Alphabet.builder()
                .setAlphabet(enigmaConfiguration.getAplhabet())
                .build();
        this.reflector = Reflector.builder()
                .setSubstitutionTable(Utils.createReflectorSubstitutionMap(alphabet))
                .build();
        RotorGroupBuilder rotorGroupBuilder = RotorGroup.builder();
        for (int i=0; i<enigmaConfiguration.getEnigmaSetup().getRotorOrdinals().size(); i++) {
            Integer rotorOrdinal = enigmaConfiguration.getEnigmaSetup().getRotorOrdinals().get(i);
            String rotorParameters = enigmaConfiguration.getRotorParameters().get(rotorOrdinal);
            Rotor rotor = Rotor.builder()
                    .setIndex(alphabet.getIndex(enigmaConfiguration.getEnigmaSetup().getRotorStartingPositions().get(i)))
                    .setSubstitutionTable(Utils.createSubstitutionMap(alphabet, rotorParameters))
                    .build();
            rotorGroupBuilder.addRotor(rotor);
        }
        this.rotorGroup = rotorGroupBuilder.build();
        this.plugBoard = PlugBoard.builder()
                .setSetup(enigmaConfiguration.getEnigmaSetup().getPlugBoardSetup())
                .build();
        return this;
    }

    public Enigma build() {
        return new EnigmaImpl(alphabet, reflector, rotorGroup, plugBoard);
    }

}
