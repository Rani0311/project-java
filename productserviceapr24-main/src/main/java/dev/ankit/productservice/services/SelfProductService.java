package dev.ankit.productservice.services;

import dev.ankit.productservice.dtos.CategoryResponceDto;
import dev.ankit.productservice.models.Category;
import dev.ankit.productservice.models.Product;
import dev.ankit.productservice.repositories.CategoryRepository;
import dev.ankit.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("selfProductService")
public class SelfProductService implements  ProductService{
    private final CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository)
    {
        this.productRepository=productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override

    public Product getSingleProduct(Long id) {
       return productRepository.findByIdIs(id);
    }

    @Override
    public Product createProduct(String title, String description, String image, String categoryTitle, double price) {
        Product product=new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);

        Category categoryFromDatabase= categoryRepository.findByTitle(categoryTitle);
        if(categoryFromDatabase==null)
        {
            Category newCategory=new Category();
            newCategory.setTitle(categoryTitle);
            categoryFromDatabase=newCategory;
           // categoryFromDatabase=categoryRepository.save(newCategory);
        }
        product.setCategory(categoryFromDatabase);




        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product deleteProduct(long id) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, String title, double price, String description, String image, String category) {
        return null;
    }

    @Override
    public List<Product> GetProductByCategory(String Title) {
        return List.of();
    }

    @Override
    public List<CategoryResponceDto> getCategories() {
        return List.of();
    }
}
