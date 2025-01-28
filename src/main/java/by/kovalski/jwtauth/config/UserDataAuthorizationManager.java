package by.kovalski.jwtauth.config;

import by.kovalski.jwtauth.entity.UserData;
import by.kovalski.jwtauth.exception.ServiceException;
import by.kovalski.jwtauth.service.JwtService;
import by.kovalski.jwtauth.service.UserDataService;
import by.kovalski.jwtauth.util.HttpRequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Component
public class UserDataAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private static final String REQUEST_VAR_NAME = "userDataId";

    private final UserDataService userDataService;
    private final JwtService jwtService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        // Long userDataId = Long.parseLong(context.getVariables().get(REQUEST_VAR_NAME)); // not working :(
        Long userDataId = Long.parseLong(context.getRequest().getRequestURI().split("/")[4]);
        String jwt = HttpRequestUtils.extractBearerToken(context.getRequest());
        Long userId = jwtService.extractUserId(jwt);
        boolean isGranted = false;
        try {
            if (userDataService.getById(userDataId).getUserId().equals(userId) ||
                    authentication
                            .get()
                            .getAuthorities()
                            .stream()
                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
            ) {
                isGranted = true;
            }
        } catch (ServiceException e) { // user data is not found
            // I'm sorry
        }
        return new AuthorizationDecision(isGranted);
    }

}
