package dev.ankit.productservice.services;

import dev.ankit.productservice.dtos.CategoryResponceDto;
import dev.ankit.productservice.models.Product;

import java.util.List;

public interface ProductService {
    public Product getSingleProduct(Long id);
    public Product createProduct(String title, String description,
                                 String image, String category, double price);

    public List<Product> getAllProducts();
    public  Product deleteProduct(long id);
    public Product updateProduct(Long id, String title, double price, String description,
                                 String image, String category);
   public List<Product>GetProductByCategory(String Title);
   public List<CategoryResponceDto>getCategories();
}
