package com.valkryst.generator;

import com.valkryst.builder.CombinatorialNameGeneratorBuilder;

import java.util.Random;

public class CombinatorialNameGenerator {
    /** The array containing all loaded name-beginnings. */
    private final String[] beginnings;
    /** The array containing all loaded name-middles. */
    private final String[] middles;
    /** The array containing all loaded name-endings. */
    private final String[] endings;

    /**
     * Constructs a new combinatorial name generator
     * from the specified builder.
     *
     * @param builder
     *         The builder to retrieve the beginnings,
     *         middles, and ends from.
     *
     * @param usesMiddles
     *         Whether or not name-middles can be used
     *         in name generation.
     */
    public CombinatorialNameGenerator(final CombinatorialNameGeneratorBuilder builder, final boolean usesMiddles) {
        final int totalBeginnings = builder.getBeginnings().size();
        final int totalEndings = builder.getEndings().size();

        beginnings = builder.getBeginnings().toArray(new String[totalBeginnings]);
        endings = builder.getEndings().toArray(new String[totalEndings]);

        if(usesMiddles) {
            final int totalMiddles = builder.getMiddles().size();
            middles = builder.getMiddles().toArray(new String[totalMiddles]);
        } else {
            middles = null;
        }
    }

    /**
     * Randomly generates a name with one beginning,
     * zero or more middles, and one ending.
     *
     * If no middles have been loaded, then they are
     * ignored and a name is generated without them.
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
     * @param middlesToUse
     *         The number of name-middles to append
     *         between the beginning and ending.
     *
     * @return
     *         The generated name.
     */
    public String generateName(final Random random, final int length, int middlesToUse) {
        if(length == 0) {
            return "LENGTH_WAS_ZERO";
        }

        if(middles == null) {
            middlesToUse = 0;
        }

        if(middlesToUse < 0) {
            middlesToUse = 0;
        }

        // Setup Variables:
        final int beginningIndex = random.nextInt(beginnings.length);
        final int endingIndex = random.nextInt(endings.length);
        final StringBuilder sb = new StringBuilder();

        // Construct Name:
        sb.append(beginnings[beginningIndex]);

        for(int i = 0 ; i < middlesToUse ; i++) {
            int middleIndex = random.nextInt(middles.length);
            sb.append(middles[middleIndex]);
        }

        sb.append(endings[endingIndex]);

        // Return Name:
        if(length >= 0 && length <= sb.length()) {
            return sb.substring(0, length);
        } else {
            return sb.toString();
        }
    }
}
