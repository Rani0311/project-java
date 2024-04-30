package dev.ankit.productservice;

import dev.ankit.productservice.models.Category;
import dev.ankit.productservice.models.Product;
import dev.ankit.productservice.repositories.CategoryRepository;
import dev.ankit.productservice.repositories.ProductRepository;
import dev.ankit.productservice.repositories.Projections.ProductWithTitleAndId;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class Productserviceapr24ApplicationTests {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Test
    void contextLoads() {
    }
    public  void testingQuery()
    {
        Product product=productRepository.getProductWithSpecificTitleAndId("electronic",1l);
        System.out.println(product.getTitle());
    }
    @Test
    public  void testingQuery2()
    {
        ProductWithTitleAndId product=productRepository.getProductWithSpecificTitleAndId2("electronic",1l);
        System.out.println(product.getId());
        System.out.println(product.getTitle());
    }
    @Test
    @Transactional
    public  void testingFatchQuery()
    {
        Category category=categoryRepository.findByTitle("mobiles");
        System.out.println(category.getTitle());
    }
    @Test
    public  void testingFatchQuery2()
    {
       Optional< Category> category=categoryRepository.findById(1L);
        System.out.println(category.get().getTitle());
    }
    @Test
    @Transactional
    public  void  nPlusOneProblem()
    {
        List<Category>categories=categoryRepository.findAll();
        for(Category category:categories)
        {
            for(Product product:category.getProducts())
            {
                System.out.println(product.getTitle());
            }
        }

    }


}
