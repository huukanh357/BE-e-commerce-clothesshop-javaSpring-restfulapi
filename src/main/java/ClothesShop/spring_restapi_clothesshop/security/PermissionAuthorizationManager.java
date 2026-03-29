package ClothesShop.spring_restapi_clothesshop.security;

import ClothesShop.spring_restapi_clothesshop.model.Permission;
import ClothesShop.spring_restapi_clothesshop.model.Role;
import ClothesShop.spring_restapi_clothesshop.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class PermissionAuthorizationManager
        implements AuthorizationManager<RequestAuthorizationContext> {

    private final RoleRepository roleRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private Map<String, List<Permission>> rolePermissionsCache = new HashMap<>();

    public PermissionAuthorizationManager(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void loadCache() {
        List<Role> roles = roleRepository.findAllWithPermissions();

        Map<String, List<Permission>> cache = new HashMap<>();
        for (Role role : roles) {
            cache.computeIfAbsent("ROLE_" + role.getName(), key -> new ArrayList<>())
                    .addAll(role.getPermissions());
        }
        this.rolePermissionsCache = cache;
    }

    @Override
    public AuthorizationDecision authorize(
            Supplier<? extends Authentication> authSupplier,
            RequestAuthorizationContext context) {

        Authentication authentication = authSupplier.get();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        String requestPath = context.getRequest().getRequestURI();
        String httpMethod = context.getRequest().getMethod();

        List<String> userRoles = getUserRolesFromJwt(authentication);

        if (userRoles.contains("ROLE_ADMIN") || userRoles.contains("ROLE_SUPER_ADMIN")) {
            return new AuthorizationDecision(true);
        }

        for (String role : userRoles) {
            List<Permission> permissions = rolePermissionsCache.getOrDefault(role, Collections.emptyList());

            for (Permission permission : permissions) {
                if (permission.getMethod().name().equalsIgnoreCase(httpMethod)
                        && pathMatcher.match(permission.getApiPath(), requestPath)) {
                    return new AuthorizationDecision(true);
                }
            }
        }

        return new AuthorizationDecision(false);
    }

    @SuppressWarnings("unchecked")
    private List<String> getUserRolesFromJwt(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtToken) {
            Object rolesClaim = jwtToken.getToken().getClaim("roles");
            if (rolesClaim instanceof List<?> roles) {
                return (List<String>) roles;
            }
        }
        return Collections.emptyList();
    }
}
