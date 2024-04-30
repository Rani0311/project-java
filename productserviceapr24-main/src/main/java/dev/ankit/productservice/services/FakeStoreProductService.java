package dev.ankit.productservice.services;

import dev.ankit.productservice.dtos.CategoryResponceDto;
import dev.ankit.productservice.dtos.FakeStoreProductDto;
import dev.ankit.productservice.models.Category;
import dev.ankit.productservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;




@Service("fakeStoreService")
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) {
//        FakeStoreProductDto fakeStoreProductDto = restTemplate
//                .getForObject(
//                        "https://fakestoreapi.com/products/" + id,
//                        FakeStoreProductDto.class);

        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class);


        FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();

        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());

        Category category = new Category();
        category.setTitle(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return product;
    }

    @Override
    public Product createProduct(String title, String description, String image, String category, double price) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setCategory(category);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setTitle(title);

        FakeStoreProductDto response = restTemplate
                .postForObject("https://fakestoreapi.com/products",
                        fakeStoreProductDto,
                        FakeStoreProductDto.class);


        return response.toProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] response =
                restTemplate.getForObject("https://fakestoreapi.com/products",
                        FakeStoreProductDto[].class);

        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: response) {
            products.add(fakeStoreProductDto.toProduct());
        }

        return products;
    }

    @Override
    public Product deleteProduct(long id) {
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(
                "https://fakestoreapi.com/products/{id}", HttpMethod.DELETE, requestCallback, responseExtractor, id);
        return response.getBody().toProduct();
    }

    @Override
    public Product updateProduct(Long id,String title,double price,String  description,String image,String category) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setCategory(category);

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(
                "https://fakestoreapi.com/products/{id}", HttpMethod.PUT, requestCallback, responseExtractor, id);

        return response.getBody().toProduct();
    }

    @Override
    public List<Product> GetProductByCategory(String Title) {
          FakeStoreProductDto[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/"+ Title,
                FakeStoreProductDto[].class);

        List<Product> product = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : response){
            product.add(fakeStoreProductDto.toProduct());
        }
        return product;

         //return null;
    }

    @Override
    public List<CategoryResponceDto> getCategories() {
        String[] stringResponse = restTemplate.getForObject("https://fakestoreapi.com/products/categories",
                String[].class);

        List<CategoryResponceDto> response = new ArrayList<>();
        for (String category : stringResponse) {
            CategoryResponceDto categoryResponseDto = new CategoryResponceDto();
            categoryResponseDto.setTitle(category);
            response.add(categoryResponseDto);
        }
        return response;
    }


}
