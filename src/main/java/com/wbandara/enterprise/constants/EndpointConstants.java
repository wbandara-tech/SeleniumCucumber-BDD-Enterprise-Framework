package com.wbandara.enterprise.constants;

/**
 * URL endpoint constants for the Automation Exercise website.
 * Centralized management of all application routes.
 */
public final class EndpointConstants {

    private EndpointConstants() {
        throw new UnsupportedOperationException("EndpointConstants is a utility class and cannot be instantiated");
    }

    // Page Endpoints
    public static final String HOME = "/";
    public static final String LOGIN = "/login";
    public static final String SIGNUP = "/signup";
    public static final String PRODUCTS = "/products";
    public static final String PRODUCT_DETAILS = "/product_details/";
    public static final String CART = "/view_cart";
    public static final String CHECKOUT = "/checkout";
    public static final String CONTACT_US = "/contact_us";
    public static final String TEST_CASES = "/test_cases";
    public static final String API_LIST = "/api_list";
    public static final String DELETE_ACCOUNT = "/delete_account";
    public static final String ACCOUNT_CREATED = "/account_created";
    public static final String LOGOUT = "/logout";
    public static final String PAYMENT = "/payment";
    public static final String PAYMENT_DONE = "/payment_done";
}

