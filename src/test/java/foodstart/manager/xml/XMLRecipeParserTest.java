package foodstart.manager.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import foodstart.manager.Managers;
import foodstart.manager.Persistence;
import foodstart.model.DataType;
import foodstart.model.DietaryRequirement;
import foodstart.model.Unit;
import foodstart.model.stock.Ingredient;

public class XMLRecipeParserTest {

	static Persistence persistence;
	
	@BeforeClass
	public static void setUp() throws Exception {
		Managers.getIngredientManager().addIngredient(Unit.UNITS, "Pickle", 9000, new HashMap<DietaryRequirement, Boolean>(), 1, 1);
		Managers.getIngredientManager().addIngredient(Unit.UNITS, "Bun", 9001, new HashMap<DietaryRequirement, Boolean>(), 1, 1);
		
		persistence = new XMLPersistence();
		persistence.importFile(new File("resources/data/TestRecipes1.xml"), DataType.RECIPE);
	}
	
	@Test
	public void testImportingRecipeCorrectId() {
		assertTrue(Managers.getRecipeManager().getRecipe(1000) != null);
	}

	@Test
	public void testImporting2Recipes() {
		assertTrue(Managers.getRecipeManager().getRecipe(1000) != null);
		assertTrue(Managers.getRecipeManager().getRecipe(1001) != null);
	}
	
	@Test
	public void testImportingRecipeCorrectName() {
		assertEquals("Pickle burger", Managers.getRecipeManager().getRecipe(1000).getDisplayName());
	}
	
	@Test
	public void testImportingRecipeCorrectIngredientIds() {
		Map<Ingredient, Integer> ingredients = Managers.getRecipeManager().getRecipe(1000).getIngredients();
		
		assertEquals(2, ingredients.size());
		Iterator<Ingredient> ingredientIterator = ingredients.keySet().iterator();
		
		Ingredient one = ingredientIterator.next();
		Ingredient two = ingredientIterator.next();
		
		assertTrue((one.getId() == 9000 && two.getId() == 9001) || (one.getId() == 9001 && two.getId() == 9000));
	}
	
	@Test
	public void testImportingRecipeCorrectIngredientQuantities() {
		Map<Ingredient, Integer> ingredients = Managers.getRecipeManager().getRecipe(1000).getIngredients();
		
		assertEquals(2, ingredients.size());
		Iterator<Ingredient> ingredientIterator = ingredients.keySet().iterator();
		
		Ingredient one = ingredientIterator.next();
		Ingredient two = ingredientIterator.next();
		
		if (one.getId() == 1000) {
			assertEquals(2, (int) ingredients.get(one));
			assertEquals(1, (int) ingredients.get(two));
		} else {
			assertEquals(1, (int) ingredients.get(one));
			assertEquals(2, (int) ingredients.get(two));
		}
	}

}
