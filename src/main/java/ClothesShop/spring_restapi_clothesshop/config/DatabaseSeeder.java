package ClothesShop.spring_restapi_clothesshop.config;

import ClothesShop.spring_restapi_clothesshop.model.Category;
import ClothesShop.spring_restapi_clothesshop.model.Permission;
import ClothesShop.spring_restapi_clothesshop.model.Role;
import ClothesShop.spring_restapi_clothesshop.model.User;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;
import ClothesShop.spring_restapi_clothesshop.repository.CategoryRepository;
import ClothesShop.spring_restapi_clothesshop.repository.PermissionRepository;
import ClothesShop.spring_restapi_clothesshop.repository.RoleRepository;
import ClothesShop.spring_restapi_clothesshop.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.seed-data", havingValue = "true")
public class DatabaseSeeder implements CommandLineRunner {

        private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);
        private static final String DEFAULT_PASSWORD = "123456";

        private final PermissionRepository permissionRepository;
        private final RoleRepository roleRepository;
        private final UserRepository userRepository;
        private final CategoryRepository categoryRepository;
        private final PasswordEncoder passwordEncoder;

        public DatabaseSeeder(
                        CategoryRepository categoryRepository,
                        PermissionRepository permissionRepository,
                        RoleRepository roleRepository,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
                this.categoryRepository = categoryRepository;
                this.permissionRepository = permissionRepository;
                this.roleRepository = roleRepository;
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) {
                seedCategories();

                if (userRepository.count() > 0) {
                        log.info(">>> Users already seeded, skipping permissions/roles/users");
                        return;
                }

                log.info(">>> Seeding database...");
                List<Permission> permissions = seedPermissions();
                List<Role> roles = seedRoles(permissions);
                seedUsers(roles);
                log.info(">>> Database seeded successfully");
        }

        private void seedCategories() {
                List<String> categoryNames = List.of(
                                "Tất cả",
                                "nổi bật",
                                "Áo phông",
                                "Áo",
                                "Sale",
                                "Quần",
                                "Quần dài",
                                "Áo khoác",
                                "Áo dài",
                                "Quần dùi",
                                "TOP",
                                "BOTTOM",
                                "OUTWEAR",
                                "ACCESORIES");

                int inserted = 0;
                for (String categoryName : categoryNames) {
                        if (!categoryRepository.existsByName(categoryName)) {
                                Category category = new Category();
                                category.setName(categoryName);
                                categoryRepository.save(category);
                                inserted++;
                        }
                }

                log.info("Seeded {} categories (inserted: {})", categoryNames.size(), inserted);
        }

        private List<Permission> seedPermissions() {
                List<Permission> permissions = new ArrayList<>();
                permissions.add(permission("VIEW_USERS", "/api/users", PermissionMethodENUM.GET, "USER"));
                permissions.add(permission("VIEW_USER", "/api/users/{id}", PermissionMethodENUM.GET, "USER"));
                permissions.add(permission("VIEW_USER_BY_USERNAME", "/api/users/username/{username}",
                                PermissionMethodENUM.GET,
                                "USER"));
                permissions.add(permission("VIEW_USER_BY_EMAIL", "/api/users/email/{email}", PermissionMethodENUM.GET,
                                "USER"));
                permissions.add(permission("CREATE_USER", "/api/users", PermissionMethodENUM.POST, "USER"));
                permissions.add(permission("UPDATE_USER", "/api/users/{id}", PermissionMethodENUM.PUT, "USER"));
                permissions.add(permission("DELETE_USER", "/api/users/{id}", PermissionMethodENUM.DELETE, "USER"));
                permissions.add(permission("GET_ME", "/api/auth/me", PermissionMethodENUM.GET, "AUTH"));
                permissions.add(permission("UPDATE_ME", "/api/auth/me/update", PermissionMethodENUM.PATCH, "AUTH"));
                permissions.add(permission("UPLOAD_ME_AVATAR", "/api/auth/me/avatar", PermissionMethodENUM.POST,
                                "AUTH"));
                permissions.add(permission("CHANGE_PASSWORD", "/api/auth/me/password", PermissionMethodENUM.PATCH,
                                "AUTH"));
                permissions.add(permission("VIEW_ROLES", "/api/roles", PermissionMethodENUM.GET, "ROLE"));
                permissions.add(permission("VIEW_ROLE", "/api/roles/{id}", PermissionMethodENUM.GET, "ROLE"));
                permissions.add(permission("VIEW_ROLE_BY_NAME", "/api/roles/name/{name}", PermissionMethodENUM.GET,
                                "ROLE"));
                permissions.add(permission("CREATE_ROLE", "/api/roles", PermissionMethodENUM.POST, "ROLE"));
                permissions.add(permission("UPDATE_ROLE", "/api/roles/{id}", PermissionMethodENUM.PUT, "ROLE"));
                permissions.add(permission("DELETE_ROLE", "/api/roles/{id}", PermissionMethodENUM.DELETE, "ROLE"));
                permissions.add(permission("CHECK_ROLE_NAME", "/api/roles/check/name", PermissionMethodENUM.GET,
                                "ROLE"));
                permissions.add(permission("VIEW_PERMISSIONS", "/api/permissions", PermissionMethodENUM.GET,
                                "PERMISSION"));
                permissions.add(permission("VIEW_PERMISSION", "/api/permissions/{id}", PermissionMethodENUM.GET,
                                "PERMISSION"));
                permissions.add(permission("VIEW_PERMISSION_BY_NAME", "/api/permissions/name/{name}",
                                PermissionMethodENUM.GET,
                                "PERMISSION"));
                permissions.add(permission("CREATE_PERMISSION", "/api/permissions", PermissionMethodENUM.POST,
                                "PERMISSION"));
                permissions
                                .add(permission("UPDATE_PERMISSION", "/api/permissions/{id}", PermissionMethodENUM.PUT,
                                                "PERMISSION"));
                permissions.add(
                                permission("DELETE_PERMISSION", "/api/permissions/{id}", PermissionMethodENUM.DELETE,
                                                "PERMISSION"));
                permissions.add(permission("CHECK_PERMISSION_NAME", "/api/permissions/check/name",
                                PermissionMethodENUM.GET,
                                "PERMISSION"));
                permissions.add(permission("VIEW_PRODUCTS", "/api/products", PermissionMethodENUM.GET, "PRODUCT"));
                permissions.add(permission("VIEW_PRODUCT", "/api/products/{id}", PermissionMethodENUM.GET, "PRODUCT"));
                permissions.add(
                                permission("VIEW_PRODUCT_BY_NAME", "/api/products/name/{name}",
                                                PermissionMethodENUM.GET, "PRODUCT"));
                permissions.add(permission("VIEW_PRODUCTS_BY_CATEGORY", "/api/products/category/{categoryName}",
                                PermissionMethodENUM.GET, "PRODUCT"));
                permissions.add(permission("VIEW_CATEGORIES", "/api/categories", PermissionMethodENUM.GET, "PRODUCT"));
                permissions.add(permission("VIEW_CATEGORY", "/api/categories/{id}", PermissionMethodENUM.GET,
                                "PRODUCT"));
                permissions.add(permission("VIEW_CATEGORY_BY_NAME", "/api/categories/name/{name}",
                                PermissionMethodENUM.GET,
                                "PRODUCT"));
                permissions.add(permission("CREATE_CATEGORY", "/api/categories", PermissionMethodENUM.POST, "PRODUCT"));
                permissions.add(permission("UPDATE_CATEGORY", "/api/categories/{id}", PermissionMethodENUM.PUT,
                                "PRODUCT"));
                permissions.add(permission("DELETE_CATEGORY", "/api/categories/{id}", PermissionMethodENUM.DELETE,
                                "PRODUCT"));
                permissions.add(permission("CHECK_CATEGORY_NAME", "/api/categories/check/name",
                                PermissionMethodENUM.GET,
                                "PRODUCT"));
                permissions.add(permission("CREATE_PRODUCT", "/api/products", PermissionMethodENUM.POST, "PRODUCT"));
                permissions.add(permission("UPDATE_PRODUCT", "/api/products/{id}", PermissionMethodENUM.PUT,
                                "PRODUCT"));
                permissions.add(permission("DELETE_PRODUCT", "/api/products/{id}", PermissionMethodENUM.DELETE,
                                "PRODUCT"));
                permissions
                                .add(permission("CHECK_PRODUCT_NAME", "/api/products/check/name",
                                                PermissionMethodENUM.GET, "PRODUCT"));
                permissions
                                .add(permission("VIEW_PRODUCT_DETAILS", "/api/product-details",
                                                PermissionMethodENUM.GET, "PRODUCT"));
                permissions.add(
                                permission("VIEW_PRODUCT_DETAIL", "/api/product-details/{id}", PermissionMethodENUM.GET,
                                                "PRODUCT"));
                permissions.add(permission("VIEW_PRODUCT_DETAIL_BY_PRODUCT", "/api/product-details/product/{productId}",
                                PermissionMethodENUM.GET, "PRODUCT"));
                permissions
                                .add(permission("CREATE_PRODUCT_DETAIL", "/api/product-details",
                                                PermissionMethodENUM.POST, "PRODUCT"));
                permissions.add(
                                permission("UPDATE_PRODUCT_DETAIL", "/api/product-details/{id}",
                                                PermissionMethodENUM.PUT, "PRODUCT"));
                permissions.add(permission("DELETE_PRODUCT_DETAIL", "/api/product-details/{id}",
                                PermissionMethodENUM.DELETE,
                                "PRODUCT"));
                permissions.add(permission("VIEW_CARTS", "/api/carts", PermissionMethodENUM.GET, "CART"));
                permissions.add(permission("VIEW_MY_CART", "/api/me/cart", PermissionMethodENUM.GET, "CART"));
                permissions.add(permission("VIEW_CART", "/api/carts/{id}", PermissionMethodENUM.GET, "CART"));
                permissions.add(permission("VIEW_CART_BY_USER", "/api/carts/user/{userId}", PermissionMethodENUM.GET,
                                "CART"));
                permissions.add(permission("CREATE_CART", "/api/carts", PermissionMethodENUM.POST, "CART"));
                permissions.add(permission("UPDATE_CART", "/api/carts/{id}", PermissionMethodENUM.PUT, "CART"));
                permissions.add(permission("DELETE_CART", "/api/carts/{id}", PermissionMethodENUM.DELETE, "CART"));
                permissions.add(permission("ADD_MY_CART_ITEM", "/api/me/cart/items", PermissionMethodENUM.POST,
                                "CART"));
                permissions.add(permission("UPDATE_MY_CART_ITEM", "/api/me/cart/items/{itemId}",
                                PermissionMethodENUM.PATCH,
                                "CART"));
                permissions.add(permission("DELETE_MY_CART_ITEM", "/api/me/cart/items/{itemId}",
                                PermissionMethodENUM.DELETE, "CART"));
                permissions.add(permission("CLEAR_MY_CART", "/api/me/cart/items",
                                PermissionMethodENUM.DELETE, "CART"));
                permissions.add(permission("VIEW_CART_DETAILS", "/api/cart-details", PermissionMethodENUM.GET, "CART"));
                permissions.add(permission("VIEW_CART_DETAIL", "/api/cart-details/{id}", PermissionMethodENUM.GET,
                                "CART"));
                permissions.add(permission("VIEW_CART_DETAILS_BY_CART", "/api/cart-details/cart/{cartId}",
                                PermissionMethodENUM.GET, "CART"));
                permissions.add(permission("CREATE_CART_DETAIL", "/api/cart-details", PermissionMethodENUM.POST,
                                "CART"));
                permissions.add(permission("UPDATE_CART_DETAIL", "/api/cart-details/{id}", PermissionMethodENUM.PUT,
                                "CART"));
                permissions
                                .add(permission("DELETE_CART_DETAIL", "/api/cart-details/{id}",
                                                PermissionMethodENUM.DELETE, "CART"));
                permissions.add(permission("VIEW_ORDERS", "/api/orders", PermissionMethodENUM.GET, "ORDER"));
                permissions.add(permission("VIEW_ORDER", "/api/orders/{id}", PermissionMethodENUM.GET, "ORDER"));
                permissions
                                .add(permission("VIEW_ORDERS_BY_USER", "/api/orders/user/{userId}",
                                                PermissionMethodENUM.GET, "ORDER"));
                permissions.add(permission("VIEW_ORDERS_BY_STATUS", "/api/orders/status", PermissionMethodENUM.GET,
                                "ORDER"));
                permissions.add(permission("CREATE_ORDER", "/api/orders", PermissionMethodENUM.POST, "ORDER"));
                permissions.add(permission("UPDATE_ORDER", "/api/orders/{id}", PermissionMethodENUM.PUT, "ORDER"));
                permissions.add(permission("DELETE_ORDER", "/api/orders/{id}", PermissionMethodENUM.DELETE, "ORDER"));
                permissions.add(permission("VIEW_MY_ORDERS", "/api/me/orders", PermissionMethodENUM.GET, "ORDER"));
                permissions.add(permission("VIEW_MY_ORDER", "/api/me/orders/{id}", PermissionMethodENUM.GET,
                                "ORDER"));
                permissions.add(permission("CREATE_MY_ORDER", "/api/me/orders", PermissionMethodENUM.POST,
                                "ORDER"));
                permissions.add(permission("VIEW_ORDER_ITEMS", "/api/order-items", PermissionMethodENUM.GET, "ORDER"));
                permissions.add(permission("VIEW_ORDER_ITEM", "/api/order-items/{id}", PermissionMethodENUM.GET,
                                "ORDER"));
                permissions.add(permission("VIEW_ORDER_ITEMS_BY_ORDER", "/api/order-items/order/{orderId}",
                                PermissionMethodENUM.GET, "ORDER"));
                permissions.add(permission("CREATE_ORDER_ITEM", "/api/order-items", PermissionMethodENUM.POST,
                                "ORDER"));
                permissions.add(permission("UPDATE_ORDER_ITEM", "/api/order-items/{id}", PermissionMethodENUM.PUT,
                                "ORDER"));
                permissions.add(permission("DELETE_ORDER_ITEM", "/api/order-items/{id}", PermissionMethodENUM.DELETE,
                                "ORDER"));
                permissions.add(permission("VIEW_PAYMENTS", "/api/payments", PermissionMethodENUM.GET, "PAYMENT"));
                permissions.add(permission("VIEW_PAYMENT", "/api/payments/{id}", PermissionMethodENUM.GET, "PAYMENT"));
                permissions.add(permission("VIEW_PAYMENT_BY_ORDER", "/api/payments/order/{orderId}",
                                PermissionMethodENUM.GET,
                                "PAYMENT"));
                permissions.add(permission("VIEW_PAYMENTS_BY_USER", "/api/payments/user/{userId}",
                                PermissionMethodENUM.GET,
                                "PAYMENT"));
                permissions.add(permission("CREATE_PAYMENT", "/api/payments", PermissionMethodENUM.POST, "PAYMENT"));
                permissions.add(permission("UPDATE_PAYMENT", "/api/payments/{id}", PermissionMethodENUM.PUT,
                                "PAYMENT"));
                permissions.add(permission("DELETE_PAYMENT", "/api/payments/{id}", PermissionMethodENUM.DELETE,
                                "PAYMENT"));
                permissions.add(permission("VIEW_MY_PAYMENTS", "/api/me/payments", PermissionMethodENUM.GET,
                                "PAYMENT"));
                permissions.add(permission("VIEW_MY_PAYMENT", "/api/me/payments/{id}", PermissionMethodENUM.GET,
                                "PAYMENT"));
                permissions.add(permission("CREATE_MY_PAYMENT", "/api/me/payments", PermissionMethodENUM.POST,
                                "PAYMENT"));

                List<Permission> saved = permissionRepository.saveAll(permissions);
                log.info("Seeded {} permissions", saved.size());
                return saved;
        }

        private List<Role> seedRoles(List<Permission> allPermissions) {
                Role admin = new Role();
                admin.setName("ADMIN");
                admin.setPermissions(new ArrayList<>(allPermissions));

                Role user = new Role();
                user.setName("USER");
                user.setPermissions(filterPermissions(
                                allPermissions,
                                "GET_ME", "UPDATE_ME", "UPLOAD_ME_AVATAR", "CHANGE_PASSWORD",
                                "VIEW_CATEGORIES", "VIEW_CATEGORY", "VIEW_CATEGORY_BY_NAME", "CHECK_CATEGORY_NAME",
                                "VIEW_PRODUCTS", "VIEW_PRODUCT", "VIEW_PRODUCT_BY_NAME", "VIEW_PRODUCTS_BY_CATEGORY",
                                "VIEW_PRODUCT_DETAILS", "VIEW_PRODUCT_DETAIL", "VIEW_PRODUCT_DETAIL_BY_PRODUCT",
                                "VIEW_MY_CART", "ADD_MY_CART_ITEM", "UPDATE_MY_CART_ITEM",
                                "DELETE_MY_CART_ITEM", "CLEAR_MY_CART",
                                "VIEW_CARTS", "VIEW_CART", "VIEW_CART_BY_USER", "CREATE_CART", "UPDATE_CART",
                                "DELETE_CART",
                                "VIEW_CART_DETAILS", "VIEW_CART_DETAIL", "VIEW_CART_DETAILS_BY_CART",
                                "CREATE_CART_DETAIL", "UPDATE_CART_DETAIL", "DELETE_CART_DETAIL",
                                "VIEW_ORDERS", "VIEW_ORDER", "VIEW_ORDERS_BY_USER", "VIEW_ORDERS_BY_STATUS",
                                "CREATE_ORDER", "UPDATE_ORDER",
                                "VIEW_MY_ORDERS", "VIEW_MY_ORDER", "CREATE_MY_ORDER",
                                "VIEW_ORDER_ITEMS", "VIEW_ORDER_ITEM", "VIEW_ORDER_ITEMS_BY_ORDER", "CREATE_ORDER_ITEM",
                                "VIEW_PAYMENTS", "VIEW_PAYMENT", "VIEW_PAYMENT_BY_ORDER", "VIEW_PAYMENTS_BY_USER",
                                "CREATE_PAYMENT", "VIEW_MY_PAYMENTS", "VIEW_MY_PAYMENT", "CREATE_MY_PAYMENT"));

                List<Role> saved = roleRepository.saveAll(List.of(admin, user));
                log.info("Seeded {} roles", saved.size());
                return saved;
        }

        private void seedUsers(List<Role> roles) {
                Role adminRole = findRole(roles, "ADMIN");
                Role userRole = findRole(roles, "USER");

                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@clothesshop.com");
                admin.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
                admin.setFullName("Administrator");
                admin.setPhone("0900000000");
                admin.setAddress("Ha Noi");
                admin.setCity("Ha Noi");
                admin.setRole(adminRole);

                User normalUser = new User();
                normalUser.setUsername("user01");
                normalUser.setEmail("user@clothesshop.com");
                normalUser.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
                normalUser.setFullName("Normal User");
                normalUser.setPhone("0911111111");
                normalUser.setAddress("Ho Chi Minh City");
                normalUser.setCity("Ho Chi Minh City");
                normalUser.setRole(userRole);

                userRepository.saveAll(List.of(admin, normalUser));
                log.info("Seeded 2 users (admin/user), default password: {}", DEFAULT_PASSWORD);
        }

        private Permission permission(String name, String apiPath, PermissionMethodENUM method, String module) {
                Permission permission = new Permission();
                permission.setName(name);
                permission.setApiPath(apiPath);
                permission.setMethod(method);
                permission.setModule(module);
                return permission;
        }

        private List<Permission> filterPermissions(List<Permission> all, String... names) {
                List<String> nameList = List.of(names);
                return all.stream().filter(permission -> nameList.contains(permission.getName())).toList();
        }

        private Role findRole(List<Role> roles, String name) {
                return roles.stream()
                                .filter(role -> role.getName().equals(name))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Role not found: " + name));
        }
}

