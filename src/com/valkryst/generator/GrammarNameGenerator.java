package com.valkryst.generator;

import java.util.List;
import java.util.Random;

public class GrammarNameGenerator {
    /** The rules of the grammar to use when generating a name. */
    private String[] rules;

    /**
     * Constructs a new GrammarNameGenerator with the
     * specified grammar rules.
     *
     * @param rules
     *         The rules of the grammar to use when generating a name.
     */
    public GrammarNameGenerator(final List<String> rules) {
        this.rules = rules.toArray(new String[rules.size()]);
    }

    /**
     * Generates a name of at-least the specified length.
     *
     * @param random
     *         The instance of Random to use when
     *         necessary.
     *
     * @param length
     *         The length of the name to generate.
     *
     *         If the value is less than or equal to
     *         zero, then this parameter is ignored.
     *
     *         No guarantee is made that the name
     *         will be exactly this length.
     *
     * @param startingSymbol
     *         The symbol to begin generating the
     *         name from.
     *
     * @return
     *         The generated name.
     */
    public String generateName(final Random random, final int length, final char startingSymbol) {
        if(length == 0) {
            return "LENGTH_WAS_ZERO";
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(startingSymbol));

        while(sb.length() < length) {
            for (int i = 0; i < sb.length(); i++) {
                char currentChar = sb.charAt(i);
                boolean isCurrentCharUpperCase = Character.isUpperCase(currentChar);

                if (isCurrentCharUpperCase) {
                    final String substitution = chooseRandomRuleOption(random, currentChar);

                    if (substitution != null) {
                        String temp = sb.toString();
                        temp = temp.replace(String.valueOf(currentChar), substitution);

                        sb.setLength(0);
                        sb.append(temp);

                        i--;
                    }
                }
            }

            if(sb.length() < length) {
                sb.setLength(0);
                sb.append(String.valueOf(startingSymbol));
            }
        }

        // Return Name:
        if(length >= 0) {
            return sb.substring(0, length);
        } else {
            return sb.toString();
        }
    }

    /**
     * Searches the rules of the grammar for the first
     * rule whose first character matches that of the
     * specified character.
     *
     * Ex:
     *      character = 'S'
     *
     *      rule = "T tt Tt"
     *      rule = "S ss Ss"
     *      rule = "S tt St"
     *
     *      The first rule is ignored and the second
     *      rule is used.
     *
     * @param random
     *         The instance of Random to use when
     *         necessary.
     *
     * @param character
     *         The identifier of the grammar rule to use.
     *
     * @return
     *         The chosen rule or null if no rule could
     *         be chosen.
     */
    private String chooseRandomRuleOption(final Random random, final char character) {
        for(final String rule : rules) {
            final String[] tokens = rule.split(" ");

            if(tokens[0].equals(String.valueOf(character))) {
                if(tokens.length > 1) {
                    final int randomOptionIndex = random.nextInt(tokens.length - 1) + 1;
                    return tokens[randomOptionIndex];
                }
            }
        }

        return null;
    }
}
