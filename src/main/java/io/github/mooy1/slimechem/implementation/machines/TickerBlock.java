package io.github.mooy1.slimechem.implementation.machines;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class TickerBlock extends SlimefunItem {
    public TickerBlock(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    
        new BlockMenuPreset(getId(), getItemName()) {
        
            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                onNewInstance(menu, b);
            }
        
            @Override
            public void init() {
                setupInv(this);
            }
        
            @Override
            public boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
                return p.hasPermission("slimefun.inventory.bypass") ||
                        SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), ProtectableAction.ACCESS_INVENTORIES);
            }
        
            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return getTransportSlots(flow);
            }
        };
    }
    
    public abstract void setupInv(@Nonnull BlockMenuPreset preset);
    
    public abstract void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b);
    
    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }
            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                TickerBlock.this.tick(b);
            }
        });
    }
    
    public abstract void tick(@Nonnull Block b);
    
    @Nonnull
    public abstract int[] getTransportSlots(@Nonnull ItemTransportFlow flow);
    
}
