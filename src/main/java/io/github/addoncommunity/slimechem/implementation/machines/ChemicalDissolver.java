package io.github.addoncommunity.slimechem.implementation.machines;

import io.github.addoncommunity.slimechem.implementation.atomic.Molecule;
import io.github.addoncommunity.slimechem.implementation.atomic.MoleculeIngredient;
import io.github.addoncommunity.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.addoncommunity.slimechem.implementation.attributes.Ingredient;
import io.github.addoncommunity.slimechem.utils.Util;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.addoncommunity.slimechem.implementation.atomic.Element;
import io.github.addoncommunity.slimechem.implementation.machines.abstractmachines.Machine;
import io.github.addoncommunity.slimechem.lists.Items;
import io.github.addoncommunity.slimechem.setup.Registry;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Dissolves {@link Molecule}s, {@link Material}s, and {@link SlimefunItemStack}s
 * into {@link Element}s, {@link Molecule}s, {@link Isotope}s
 * 
 * @author Mooy1
 * 
 * TODO: remove SlimefunItem#getBtItem calls and some instance ofs
 *
 */
public class ChemicalDissolver extends Machine {
    
    public static final int ENERGY = 9;
    private static final int[] inputSlots = {2, 3, 4, 5, 6};
    private static final int[] outputSlots = {38, 39, 40, 41, 42, 47, 48, 49, 50, 51};
    private static final int[] inputBorder = {1, 10, 11, 12, 13, 14, 15, 16, 7, 22};
    private static final int[] outputBorder = {28, 29, 30, 31, 32, 33, 34, 37, 43, 46, 52};
    private static final int[] background = {0, 8, 9, 17, 18, 19, 20, 21, 23, 24, 25, 26, 27, 35, 36, 44, 45, 53};
    
    private static final Map<Material, Map<Integer, MoleculeIngredient>> vanillaRecipes = new HashMap<>();
    private static final Map<String, Map<Integer, MoleculeIngredient>> slimefunRecipes = new HashMap<>();

    static {
        // Ores
        addRecipe(Material.COAL_ORE, new int[]{90, 10},
            Element.CARBON.asIngredient(12),
            Molecule.SILICON_DIOXIDE.asIngredient(5)
        );
        addRecipe(Material.IRON_ORE, new int[]{50, 30, 10, 6, 4},
            Molecule.IRON_III_OXIDE.asIngredient(4),
            Molecule.IRON_II_OXIDE.asIngredient(4),
            Molecule.SILICON_DIOXIDE.asIngredient(5),
            Molecule.IRON_PERSULFIDE.asIngredient(7),
            Molecule.COPPER_IRON_SULFIDE.asIngredient(6)
        );
        addRecipe(Material.GOLD_ORE, new int[]{70, 20, 10},
            Molecule.GOLD_TELLURIDE.asIngredient(4),
            Molecule.GOLD_ANTIMONIDE.asIngredient(4),
            Molecule.SILICON_DIOXIDE.asIngredient(5)
        );
        addRecipe(Material.REDSTONE_ORE, new int[]{60, 20, 10, 10},
            Molecule.COPPER_I_OXIDE.asIngredient(4),
            Molecule.PENTACOPPER_IRON_TETRASULFIDE.asIngredient(3),
            Molecule.COPPER_II_OXIDE.asIngredient(3),
            Molecule.SILICON_DIOXIDE.asIngredient(5)
        );
        addRecipe(Material.EMERALD_ORE, new int[]{90, 10},
            Molecule.BERYLLIUM_ALUMINUM_CYCLOSILICATE.asIngredient(6),
            Molecule.SILICATE.asIngredient(9)
        );
        addRecipe(Material.ANCIENT_DEBRIS, new int[]{80, 20},
            Molecule.SEABORGIUM_III_OXIDE.asIngredient(4),
            Molecule.SILICON_DIOXIDE.asIngredient(25)
        );

        // Plant matter

        // Wood
        // Standard: 1 plank = 2 cellulose
        // So 1 stick = 1 cellulose, 1 log = 8 cellulose, etc
        for (Material mat : Tag.LOGS.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(8));
        }
        for (Material mat : Tag.PLANKS.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(2));
        }
        // Loss of 1 cellulose
        for (Material mat : Tag.SIGNS.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(4));
        }
        for (Material mat : Tag.ITEMS_BOATS.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(10));
        }
        for (Material mat : Tag.WOODEN_BUTTONS.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(2));
        }
        for (Material mat : Tag.WOODEN_SLABS.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient());
        }
        for (Material mat : Tag.WOODEN_DOORS.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(2));
        }
        for (Material mat : Tag.WOODEN_PRESSURE_PLATES.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(4));
        }
        for (Material mat : Tag.WOODEN_STAIRS.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(3));
        }
        // loss of 1 cellulose
        for (Material mat : Tag.WOODEN_FENCES.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(3));
        }
        for (Material mat : Tag.FENCE_GATES.getValues()) {
            addRecipe(mat, new int[]{100}, Molecule.CELLULOSE.asIngredient(8));
        }
        addRecipe(Material.STICK, new int[]{100}, Molecule.CELLULOSE.asIngredient());

        // Crops
        addRecipe(Material.WHEAT, new int[]{90, 10},
            Molecule.CELLULOSE.asIngredient(2),
            Molecule.PROTEIN.asIngredient(2)
        );
    }
    
    private static Map<Integer, MoleculeIngredient> makeRecipe(int[] chances, MoleculeIngredient... ingredients) {
        Map<Integer, MoleculeIngredient> map = new HashMap<>();
        for (int i = 0 ; i < chances.length ; i++) {
            map.put(chances[i], ingredients[i]);
        }
        return map;
    }
    
    public static void addRecipe(Material mat, int[] chances, MoleculeIngredient... ingredients) {
        vanillaRecipes.put(mat, makeRecipe(chances, ingredients));
    }

    public static void addRecipe(String id, int[] chances, MoleculeIngredient... ingredients) {
        slimefunRecipes.put(id, makeRecipe(chances, ingredients));
    }
    
    // No idea what thi will be used for, but seems useful
    public static void addRecipe(Ingredient ingredient, int[] chances, MoleculeIngredient... ingredients) {
        slimefunRecipes.put(ingredient.getItem().getItemId(), makeRecipe(chances, ingredients));
    }

    public static void addRecipe(@Nonnull SlimefunItemStack slimefunItemStack, int[] chances, MoleculeIngredient... ingredients) {
        slimefunRecipes.put(slimefunItemStack.getItemId(), makeRecipe(chances, ingredients));
    }

    public ChemicalDissolver() {
        super(Items.CHEMICAL_DISSOLVER, ENERGY, ENERGY * 64, inputSlots, outputSlots, new ItemStack[] {

        });
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset preset) {
        for (int i : inputBorder) {
            preset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : outputBorder) {
            preset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : background) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    public void process(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Location l) {
        for (int slot : inputSlots) {
            ItemStack input = menu.getItemInSlot(slot);

            if (input == null) {
                continue;
            }

            int amount = 0;

            SlimefunItem item = SlimefunItem.getByItem(input);
            
            if (item != null) {

                Ingredient ingredient = Registry.getITEMS().get(item);

                if (ingredient != null) {
                    if (ingredient instanceof Molecule) {

                        Molecule molecule = (Molecule) ingredient;

                        loop: for (int i = 0 ; i < getMax(l, input) ; i++) {

                            for (MoleculeIngredient check : molecule.getIngredients()) {
                                ItemStack[] outputs = check.getNewItems();

                                for (ItemStack output : outputs) {
                                    if (menu.fits(output)) {
                                        menu.pushItem(output, outputSlots);
                                        amount++;
                                    } else {
                                        break loop;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Map<Integer, MoleculeIngredient> outputs = slimefunRecipes.get(item.getId());

                    if (outputs != null) { //sf recipe
                        output(amount, menu, l, input, outputs);
                    } else {
                        continue;
                    }
                }

            } else {
                Map<Integer, MoleculeIngredient> outputs = vanillaRecipes.get(input.getType());

                if (outputs != null) { //vanilla recipe
                    amount = output(amount, menu, l, input, outputs);
                } else {
                    continue;
                }
            }

            menu.consumeItem(slot, amount);
            break;
        }
    }
    
    private int output(int amount, BlockMenu menu, Location l, ItemStack input, Map<Integer, MoleculeIngredient> map) {
        loop: for (int i = 0 ; i < getMax(l, input) ; i++) {
            MoleculeIngredient ingredient = Util.chooseRandom(map);
            amount++;
            
            if (ingredient != null) {
                ItemStack[] outputs = ingredient.getNewItems();
                
                for (ItemStack output : outputs) {
                    if (menu.fits(output, outputSlots)) {
                        menu.pushItem(output, outputSlots);
                    } else {
                        amount--;
                        break loop;
                    }
                }
            }
        }
        return amount;
    }
    
    private int getMax(Location l, ItemStack input) {
        return (int) Math.min(input.getAmount(), Math.floor((float) getCharge(l) / ENERGY));
    }
    
}