package minetweaker.api.compat;

import java.util.List;

public class DummyJEIRecipeRegistry implements IJEIRecipeRegistry {
	
	@Override
	public void addRecipe(Object object) {
		System.out.println("Dummy cannot add recipe!");
	}
	
	@Override
	public void removeRecipe(Object object) {
		System.out.println("Dummy cannot remove recipe!");
	}
	
	@Override
	public void addFurnace(List<Object> inputs, Object output) {
		System.out.println("Dummy cannot add recipe!");
	}
	
	@Override
	public void removeFurnace(Object object) {
		System.out.println("Dummy cannot remove recipe!");
	}
}
