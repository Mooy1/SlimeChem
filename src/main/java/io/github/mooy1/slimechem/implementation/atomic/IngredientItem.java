package io.github.mooy1.slimechem.implementation.atomic;

import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A {@link SlimefunItem} to hold any class implementing {@link Ingredient}
 *
 * @author Seggan
 */

public class IngredientItem extends SlimefunItem implements NotPlaceable {

    @Getter
    private Ingredient ingredient;
    private static final Map<Ingredient, Consumer<Player>> interactActions = getActions();
    private static final Set<Ingredient> radioactiveItems = new HashSet<>();

    static {
        // Add all elements with atomic number > 82 (higher than lead)
        for (Element element : Element.values()) {
            if (element.getNumber() > 82) {
                radioactiveItems.add(element);
            }
        }
        // Technetium and promethium
        radioactiveItems.add(Element.TECHNETIUM);
        radioactiveItems.add(Element.PROMETHIUM);
        // Radioactive isotopes
        for (Isotope isotope : Isotope.values()) {
            if (isotope.isRadioactive()) {
                radioactiveItems.add(isotope);
            }
        }
    }

    public IngredientItem(Category category, Ingredient ingredient, RecipeType recipeType, ItemStack[] recipe) {
        super(category, ingredient.getItem(), recipeType, recipe);

        this.ingredient = ingredient;
        addItemHandler(onUse());
    }

    private ItemUseHandler onUse() {
        return e -> {
            Player p = e.getPlayer();
            Consumer<Player> run = interactActions.get(ingredient);
            if (run != null) {
                PlayerInventory inv = p.getInventory();
                inv.setItemInMainHand(consumeItem(inv.getItemInMainHand()));
                run.accept(p);
            }
        };
    }

    private static Map<Ingredient, Consumer<Player>> getActions() {
        Map<Ingredient, Consumer<Player>> actions = new HashMap<>();

        // TODO: add actions

        return actions;
    }

    private static ItemStack consumeItem(ItemStack stack) {
        ItemStack newStack = stack.clone();
        newStack.setAmount(stack.getAmount() - 1);
        return newStack;
    }
}