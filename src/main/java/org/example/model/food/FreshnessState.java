package org.example.model.food;

/**
 * Enumeration of food freshness states.
 * <p>
 * Represents the freshness condition of a food item, which affects its nutritional value
 * and whether characters can consume it. Some foods have freshness status while others do not.
 * </p>
 * <ul>
 *     <li>FRESH - Food is freshly prepared and at optimal quality</li>
 *     <li>PASSABLY_FRESH - Food is still edible but starting to age</li>
 *     <li>NOT_FRESH - Food has aged and is no longer edible</li>
 *     <li>NOT_APPLICABLE - Freshness does not apply to this food (magic items, preserved foods)</li>
 * </ul>
 */
public enum FreshnessState {
    FRESH,            
    PASSABLY_FRESH,
    NOT_FRESH,
    NOT_APPLICABLE
}

