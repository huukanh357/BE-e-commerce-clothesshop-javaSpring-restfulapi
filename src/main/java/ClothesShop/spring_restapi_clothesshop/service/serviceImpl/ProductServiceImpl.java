package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.dto.product.ProductCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductFilterRequest;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductResponse;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.file.FileUploadResponse;
import ClothesShop.spring_restapi_clothesshop.exception.DuplicateResourceException;
import ClothesShop.spring_restapi_clothesshop.exception.FileUploadException;
import ClothesShop.spring_restapi_clothesshop.exception.ResourceNotFoundException;
import ClothesShop.spring_restapi_clothesshop.model.Category;
import ClothesShop.spring_restapi_clothesshop.model.Product;
import ClothesShop.spring_restapi_clothesshop.repository.CategoryRepository;
import ClothesShop.spring_restapi_clothesshop.repository.ProductRepository;
import ClothesShop.spring_restapi_clothesshop.repository.ProductSpecification;
import ClothesShop.spring_restapi_clothesshop.service.FileService;
import ClothesShop.spring_restapi_clothesshop.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final ObjectMapper objectMapper;

    public ProductServiceImpl(ProductRepository productRepository,
            CategoryRepository categoryRepository,
            FileService fileService,
            ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
        this.objectMapper = objectMapper;
    }

    @Override
    @CacheEvict(cacheNames = { "productById", "productByName", "productsPage",
            "productsByCategoryPage" }, allEntries = true)
    public ProductResponse createProduct(ProductCreateRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Product", "name", request.getName());
        }

        Product product = new Product();
        product.setCategories(resolveCategories(request.getCategoryNames()));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());

        Product savedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productById", key = "#id")
    public ProductResponse getProductById(Long id) {
        return ProductResponse.fromEntity(findProductByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productByName", key = "#name")
    public ProductResponse getProductByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "name", name));
        return ProductResponse.fromEntity(product);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productsPage")
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productsByCategoryPage")
    public Page<ProductResponse> getProductsByCategory(String categoryName, Pageable pageable) {
        return productRepository.findByCategoryName(categoryName, pageable).map(ProductResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> filterProducts(ProductFilterRequest filterRequest, Pageable pageable) {
        return productRepository.findAll(
                ProductSpecification.filter(
                        filterRequest.getMinPrice(),
                        filterRequest.getMaxPrice(),
                        filterRequest.getCategoryNames(),
                        filterRequest.getSizes()),
                pageable).map(ProductResponse::fromEntity);
    }

    @Override
    @CacheEvict(cacheNames = { "productById", "productByName", "productsPage",
            "productsByCategoryPage" }, allEntries = true)
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = findProductByIdOrThrow(id);

        if (request.getName() != null && !request.getName().equals(product.getName())
                && productRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Product", "name", request.getName());
        }

        if (request.getCategoryNames() != null) {
            product.setCategories(resolveCategories(request.getCategoryNames()));
        }
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getStockQuantity() != null) {
            product.setStockQuantity(request.getStockQuantity());
        }
        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }

        Product updatedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(updatedProduct);
    }

    @Override
    @CacheEvict(cacheNames = { "productById", "productByName", "productsPage",
            "productsByCategoryPage" }, allEntries = true)
    public ProductResponse uploadProductImages(Long id, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new FileUploadException("Không có file được cung cấp");
        }

        Product product = findProductByIdOrThrow(id);

        List<String> newUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            FileUploadResponse uploaded = fileService.upload(file, "products");
            newUrls.add(uploaded.getFileUrl());
        }

        List<String> imageUrls = parseImageUrls(product.getImageUrl());
        imageUrls.addAll(newUrls);
        product.setImageUrl(serializeImageUrls(imageUrls));

        Product updatedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(updatedProduct);
    }

    @Override
    @CacheEvict(cacheNames = { "productById", "productByName", "productsPage",
            "productsByCategoryPage" }, allEntries = true)
    public void deleteProduct(Long id) {
        findProductByIdOrThrow(id);
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    private List<String> parseImageUrls(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ArrayList<>();
        }

        String trimmed = raw.trim();
        if (trimmed.startsWith("[")) {
            try {
                return new ArrayList<>(objectMapper.readValue(trimmed, new TypeReference<List<String>>() {
                }));
            } catch (JsonProcessingException ex) {
                throw new FileUploadException("Dữ liệu image_url không hợp lệ");
            }
        }

        List<String> urls = new ArrayList<>();
        urls.add(trimmed);
        return urls;
    }

    private String serializeImageUrls(List<String> urls) {
        try {
            return objectMapper.writeValueAsString(urls);
        } catch (JsonProcessingException ex) {
            throw new FileUploadException("Không thể lưu danh sách ảnh sản phẩm");
        }
    }

    private List<Category> resolveCategories(List<String> categoryNames) {
        if (categoryNames == null || categoryNames.isEmpty()) {
            throw new IllegalArgumentException("Danh mục không được để trống");
        }

        List<Category> categories = new ArrayList<>();
        for (String rawName : categoryNames) {
            String name = rawName == null ? "" : rawName.trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Danh mục không hợp lệ");
            }

            Category category = categoryRepository.findByName(name)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(name);
                        return categoryRepository.save(newCategory);
                    });
            categories.add(category);
        }
        return categories;
    }
}
