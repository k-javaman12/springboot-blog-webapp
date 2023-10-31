package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Category;
import net.javaguides.springboot.entity.Comment;
import net.javaguides.springboot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    List<Post> findByCategory_Name(String categoryName);
}
