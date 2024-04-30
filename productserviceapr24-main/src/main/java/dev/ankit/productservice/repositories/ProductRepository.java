package dev.ankit.productservice.repositories;

import dev.ankit.productservice.models.Product;
import dev.ankit.productservice.repositories.Projections.ProductWithTitleAndId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

     Product  save(Product entity);

     Product findByIdIs(Long id);


     List<Product> findAll();

    @Query("select  p from  Product  p where  p.category.title =:title and p.id=:id")
     Product getProductWithSpecificTitleAndId(@Param("title") String title,@Param("id") Long id);

     @Query("select  p.id,p.title from  Product  p where  p.category.title =:title and p.id=:id")
     ProductWithTitleAndId getProductWithSpecificTitleAndId2(@Param("title") String title, @Param("id") Long id);
}
