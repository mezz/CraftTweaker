package crafttweaker.mods.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.util.*;


@mezz.jei.api.JEIPlugin
public class JEIAddonPlugin implements IModPlugin {
    
    public static IJeiHelpers jeiHelpers;
    public static IIngredientRegistry itemRegistry;
    public static IRecipeRegistry recipeRegistry;
    public static IIngredientHelper<ItemStack> ingredientHelper;
    public static IModRegistry modRegistry;
    public static IJeiRuntime jeiRuntime;
    public static ISubtypeRegistry subtypeRegistry;

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        this.subtypeRegistry = subtypeRegistry;
    }
    
    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
    
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
    
    }
    
    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        itemRegistry = registry.getIngredientRegistry();
        ingredientHelper = itemRegistry.getIngredientHelper(VanillaTypes.ITEM);
        modRegistry = registry;
        JEIMod.onRegistered();
        
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
        recipeRegistry = iJeiRuntime.getRecipeRegistry();
        jeiRuntime = iJeiRuntime;
        JEIMod.applyActions();
    }
    
    public static List<ItemStack> getSubTypes(ItemStack stack) {
        if(JEIAddonPlugin.ingredientHelper == null) {
            return Collections.singletonList(stack);
        }
        return JEIAddonPlugin.ingredientHelper.expandSubtypes(Collections.singletonList(stack));
    }

    public static boolean hasSubtypeInterpreter(ItemStack stack) {
        if(JEIAddonPlugin.subtypeRegistry == null) {
            return false;
        }
        return JEIAddonPlugin.subtypeRegistry.hasSubtypeInterpreter(stack);
    }

    public static void useNbtForSubtypes(ItemStack stack) {
        if(JEIAddonPlugin.subtypeRegistry != null) {
            JEIAddonPlugin.subtypeRegistry.useNbtForSubtypes(stack.getItem());
        }
    }
}
