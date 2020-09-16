package com.internet.shop.web.filters;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService =
            (UserService) injector.getInstance(UserService.class);
    private Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/user/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/user/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/product/add", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/manage", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/cart/products", Set.of(Role.RoleName.USER));
        protectedUrls.put("/cart/products/add", Set.of(Role.RoleName.USER));
        protectedUrls.put("/cart/products/delete", Set.of(Role.RoleName.USER));
        protectedUrls.put("/orders/complete", Set.of(Role.RoleName.USER));
        protectedUrls.put("/user/orders", Set.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url = req.getServletPath();
        if (protectedUrls.get(url) == null) {
            filterChain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.get(userId);
        if (isAuthorized(user, protectedUrls.get(url))) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
