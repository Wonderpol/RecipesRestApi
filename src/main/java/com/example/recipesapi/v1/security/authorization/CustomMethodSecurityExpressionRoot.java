package com.example.recipesapi.v1.security.authorization;

import com.example.recipesapi.v1.security.model.CustomUserDetails;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot
        extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean isOwner(Long recipeId) {
        CustomUserDetails user = (CustomUserDetails) this.getPrincipal();
        return user.getUser()
                .getRecipes()
                .stream()
                .anyMatch(recipe -> recipe.getId().equals(recipeId));
    }

    @Override
    public void setFilterObject(final Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(final Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
