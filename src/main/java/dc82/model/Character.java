package dc82.model;

import dc82.util.RandomUtil;

/**
 * A named character generated from a {@link CharacterTemplate}.
 * The name is auto-generated (2–4 syllables, ~4–8 characters) and
 * the template's race + additional effects are applied on construction.
 */
public class Character extends Creature {

    // No-arg constructor for LibGDX Json deserialization only
    public Character() {
        super();
    }

    public Character(CharacterTemplate template) {
        super(RandomUtil.randomName(2, 4), template.getRandomRace());
        applyEffects(template.getRandomEffects());
    }
}
