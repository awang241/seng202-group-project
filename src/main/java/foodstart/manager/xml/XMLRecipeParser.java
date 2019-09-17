package foodstart.manager.xml;

import foodstart.manager.Managers;
import foodstart.manager.exceptions.DuplicateDataException;
import foodstart.manager.exceptions.IDLeadsNowhereException;
import foodstart.model.DataType;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses recipe XML files
 *
 * @author Alex Hobson on 06/09/2019
 */
public class XMLRecipeParser extends XMLParser {

	/**
	 * Constructor for recipe parser
	 */
	public XMLRecipeParser() {
		super(DataType.RECIPE);
	}

	/**
	 * Imports a recipe file
	 *
	 * @param doc The XML document to parse
	 */
	@Override
	public void parse(Document doc) {
		NodeList recipeNodes = doc.getChildNodes();
		for (int j = 0; j < recipeNodes.getLength(); j++) {
			if (recipeNodes.item(j) instanceof Element && recipeNodes.item(j).getNodeName().equalsIgnoreCase("recipes")) {
				if (recipeNodes.item(0).getNodeName().equalsIgnoreCase("recipes")) {
					Element element = (Element) recipeNodes.item(j);
					NodeList nodes = element.getElementsByTagName("recipe");
					for (int i = 0; i < nodes.getLength(); i++) {
						Node recipeNode = nodes.item(i);
						if (recipeNode instanceof Element) {
							parseOneRecipe((Element) recipeNode);
						}
					}
				}
			}
		}
	}

	/**
	 * Parses a single recipe from the given element
	 *
	 * @param element The XML element to parse
	 * @return Recipe that was parsed (also added to the registry)
	 */
	private int parseOneRecipe(Element element) {
		int recipeId = Integer.parseInt(element.getElementsByTagName("recipe_id").item(0).getTextContent());
		String name = element.getElementsByTagName("name").item(0).getTextContent();
		String method = element.getElementsByTagName("method").item(0).getTextContent();
		float price = Float.parseFloat(element.getElementsByTagName("price").item(0).getTextContent());
		NodeList ingredientsNodes = ((Element) element.getElementsByTagName("ingredients").item(0)).getElementsByTagName("ingredient");
		Map<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();

		for (int i = 0; i < ingredientsNodes.getLength(); i++) {
			Node ingredientNode = ingredientsNodes.item(i);
			if (ingredientNode instanceof Element) {
				Element ingredientElement = (Element) ingredientNode;
				int ingredientId = Integer.parseInt(ingredientElement.getElementsByTagName("ingredient_id").item(0).getTextContent());
				int quantity = Integer.parseInt(ingredientElement.getElementsByTagName("quantity").item(0).getTextContent());
				Ingredient ingredient = Managers.getIngredientManager().getIngredient(ingredientId);
				if (ingredient == null) {
					throw new IDLeadsNowhereException(DataType.INGREDIENT, ingredientId);
				}
				ingredients.put(ingredient, quantity);
			}
		}

		for (PermanentRecipe recipe : Managers.getRecipeManager().getRecipes().values()) {
			if (recipe.getDisplayName().equals(name)) {
				throw new DuplicateDataException(DataType.RECIPE, name);
			}
		}
		Managers.getRecipeManager().addRecipe(recipeId, name, method, price, ingredients);
		return recipeId;
	}

}
