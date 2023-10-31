package net.javaguides.springboot.Component;

import net.javaguides.springboot.entity.Category;
import net.javaguides.springboot.repository.CategoryRepository;
import net.javaguides.springboot.util.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryInitializer implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    public CategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        for (CategoryType type : CategoryType.values()) {
            if (!categoryRepository.existsByName(type.getDisplayName())) {
                Category category = new Category();
                category.setName(type.getDisplayName());
                categoryRepository.save(category);
            }
        }
    }
}