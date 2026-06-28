package io.github.williamandradesantana.restaurant.services;

import io.github.williamandradesantana.restaurant.domain.entity.ProductCategoryEntity;
import io.github.williamandradesantana.restaurant.domain.entity.ProductEntity;
import io.github.williamandradesantana.restaurant.dtos.ProductRequest;
import io.github.williamandradesantana.restaurant.dtos.ProductResponse;
import io.github.williamandradesantana.restaurant.repositories.ProductCategoryRepository;
import io.github.williamandradesantana.restaurant.repositories.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Cacheable(value = "products")
    public Page<ProductResponse> listProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductResponse::fromEntity);
    }

    @Cacheable(value = "oneProduct", key = "#id")
    public ProductResponse getOneProduct(Long id) {
        ProductEntity product = searchProductById(id);
        return ProductResponse.fromEntity(product);
    }

    @Caching(evict = @CacheEvict(value = "products", allEntries = true))
    public ProductResponse createProduct(ProductRequest request) {
        ProductCategoryEntity category = searchCategoryById(request.categoryId());
        ProductEntity product = request.toEntity(category);

        ProductEntity productSaved = productRepository.save(product);

        return ProductResponse.fromEntity(productSaved);
    }

    @Caching(
        put = { @CachePut(value = "oneProduct", key = "#id") },
        evict = { @CacheEvict(value = "products", allEntries = true) }
    )
    public ProductResponse updateOneProduct(Long id, ProductRequest request) {
        ProductEntity product = searchProductById(id);
        ProductCategoryEntity category = searchCategoryById(request.categoryId());

        request.insert(product, category);

        ProductEntity productUpdated = productRepository.save(product);
        return ProductResponse.fromEntity(productUpdated);
    }

    @Caching(evict = {
        @CacheEvict(value = "oneProduct", key = "#id"),
        @CacheEvict(value = "products", allEntries = true)
    })
    public void delete(Long id) {
        ProductEntity product = searchProductById(id);
        productRepository.delete(product);
    }

    private ProductCategoryEntity searchCategoryById(Long id) {
        return productCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found!"));
    }
    private ProductEntity searchProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found!"));
    }
}
