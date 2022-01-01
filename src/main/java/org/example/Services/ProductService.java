package org.example.Services;

import org.example.Objects.Product;
import org.example.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    public List<Product> allProducts(){
        List<Product> products = new ArrayList<Product>();
        productRepository.findAll().forEach(product -> {
            products.add(product);
        });
        return products;
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public Product getProductById(String id){
       return  productRepository.findById(id).orElseThrow(()-> new RuntimeException("İlgili Kayıt Bulunamadı"));
    }

    public List<Product> findProductByName(String name){
        List<Product> products = new ArrayList<Product>();
        productRepository.findByName(name).forEach(product -> {
            products.add(product);
        });
        return products;
    }

    public Product updateProductById(String id,Product updateProduct){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("id", id).minimumShouldMatch("100%"))
                .build();

        SearchHits<Product> products =
                elasticsearchOperations.search(searchQuery, Product.class, IndexCoordinates.of("productindex"));
        Product product=  products.getSearchHit(0).getContent();
        product=updateProduct;
        productRepository.save(product);
        return product;
    }

    public void deleteProductById(String id){
        productRepository.deleteById(id);
    }
}
