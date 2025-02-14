package by.kovalski.jwtauth.config;

import by.kovalski.jwtauth.entity.Role;
import by.kovalski.jwtauth.exception.ServiceException;
import by.kovalski.jwtauth.service.UserDataService;
import by.kovalski.jwtauth.util.RequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Component
public class UserDataManagementAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private static final String REQUEST_VAR_NAME = "userDataId";
    private static final short RESOURCE_ID_QUERY_POSITION = 4;

    private final UserDataService userDataService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        // Long userDataId = Long.parseLong(context.getVariables().get(REQUEST_VAR_NAME)); // not working :(
        HttpServletRequest request = context.getRequest();
        Long userDataId = Long.parseLong(request.getRequestURI().split("/")[RESOURCE_ID_QUERY_POSITION]);
        Object userId = context.getRequest().getAttribute(RequestAttributes.USER_ID);
        boolean isGranted = false;
        try {

            String userDataUserId = userDataService.getById(userDataId).getUserId();
            // TODO think about admin access
            if (userDataService.getById(userDataId).getUserId().equals(userId)) {
                isGranted = true;
            }
        } catch (ServiceException e) { // user data is not found
            // I'm sorry
        }
        return new AuthorizationDecision(isGranted);
    }

}
