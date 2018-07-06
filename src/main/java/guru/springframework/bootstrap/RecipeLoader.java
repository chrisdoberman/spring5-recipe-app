package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RecipeLoader implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public RecipeLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("seeding db with test data");
        recipeRepository.saveAll(getRecipes());
    }

    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        // perfect guac
        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setServings(4);
        perfectGuacamole.setSource("Simply Recipes");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole");
        perfectGuacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl." +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste." +
                "\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");

        perfectGuacamole.setDifficulty(Difficulty.EASY);

        Notes pgNotes = new Notes();
        pgNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your " +
                "mashed avocados.");
        perfectGuacamole.setNotes(pgNotes);

        perfectGuacamole.getCategories().add(categoryRepository.findByDescription("Mexican").get());
        perfectGuacamole.getCategories().add(categoryRepository.findByDescription("American").get());

        perfectGuacamole.addIngredient(createIngredient(2, "ripe avocados", "Each"));
        perfectGuacamole.addIngredient(createIngredient(0.5, "Kosher salt", "Teaspoon"));
        perfectGuacamole.addIngredient(createIngredient(1, "fresh lime juice or lemon juice", "Tablespoon"));
        perfectGuacamole.addIngredient(createIngredient(2, "minced red onion or thinly sliced green onion", "Tablespoon"));
        perfectGuacamole.addIngredient(createIngredient(1, "serrano chiles, stems and seeds removed, minced", "Each"));
        perfectGuacamole.addIngredient(createIngredient(2, "cilantro (leaves and tender stems), finely chopped", "Tablespoon"));
        perfectGuacamole.addIngredient(createIngredient(1, "freshly grated black pepper", "Dash"));
        perfectGuacamole.addIngredient(createIngredient(0.5, "ripe tomato, seeds and pulp removed, chopped", "Each"));

        recipes.add(perfectGuacamole);


        // spicy chicken tacos
        Recipe spicyTacos = new Recipe();
        spicyTacos.setDescription("Spicy grilled chicken tacos!");
        spicyTacos.setPrepTime(20);
        spicyTacos.setCookTime(15);
        spicyTacos.setServings(4);
        spicyTacos.setSource("Simply Recipes");
        spicyTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyTacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings." +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        spicyTacos.setDifficulty(Difficulty.EASY);

        Notes stNotes = new Notes();
        stNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");

        spicyTacos.setNotes(stNotes);

        spicyTacos.getCategories().add(categoryRepository.findByDescription("Mexican").get());
        spicyTacos.getCategories().add(categoryRepository.findByDescription("American").get());

        spicyTacos.addIngredient(createIngredient(2, "ancho chili powder", "Tablespoon"));
        spicyTacos.addIngredient(createIngredient(1, "dried oregano", "Teaspoon"));
        spicyTacos.addIngredient(createIngredient(1, "dried cumin", "Teaspoon"));
        spicyTacos.addIngredient(createIngredient(1, "sugar", "Teaspoon"));
        spicyTacos.addIngredient(createIngredient(0.5, "salt", "Teaspoon"));
        spicyTacos.addIngredient(createIngredient(1, "clove garlic, finely chopped", "Each"));
        spicyTacos.addIngredient(createIngredient(1, "finely grated orange zest", "Tablespoon"));
        spicyTacos.addIngredient(createIngredient(3, "fresh-squeezed orange juice", "Tablespoon"));
        spicyTacos.addIngredient(createIngredient(2, "olive oil", "Tablespoon"));
        spicyTacos.addIngredient(createIngredient(4, "skinless, boneless chicken thighs", "Each"));
        spicyTacos.addIngredient(createIngredient(8, "small corn tortillas", "Each"));
        spicyTacos.addIngredient(createIngredient(3, "packed baby arugula", "Cup"));
        spicyTacos.addIngredient(createIngredient(2, "medium ripe avocados, sliced", "Each"));
        spicyTacos.addIngredient(createIngredient(4, "radishes, thinly sliced", "Each"));
        spicyTacos.addIngredient(createIngredient(0.5, "cherry tomatoes, halved", "Pint"));
        spicyTacos.addIngredient(createIngredient(.25, "pint cherry tomatoes, halved", "Each"));
        spicyTacos.addIngredient(createIngredient(1, "Roughly chopped cilantro", "Each"));
        spicyTacos.addIngredient(createIngredient(0.5, "sour cream thinned with 1/4 cup milk", "Cup"));
        spicyTacos.addIngredient(createIngredient(1, "lime, cut into wedges", "Each"));

        recipes.add(spicyTacos);

        return recipes;

    }

    private Ingredient createIngredient(double amount, String description, String unitOfMeasure) {
        UnitOfMeasure uom = this.unitOfMeasureRepository.findByDescription(unitOfMeasure).get();
        Ingredient ingredient = new Ingredient(BigDecimal.valueOf(amount), description, uom);

        return ingredient;
    }

}
