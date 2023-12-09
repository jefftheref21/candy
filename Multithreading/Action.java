public enum Action {
    BUYER,
    SELLER,

    SIGNUP,
    LOGIN,

    USERNAME,
    PASSWORD,
    INVALID_CREDENTIALS,
    VALID_CREDENTIALS_BUYER,
    VALID_CREDENTIALS_SELLER,

    CANDY_PAGE,
    BUY_ITEM, // for server
    INVALID_QUANTITY,
    ADD_TO_CART,
    BACK,

    SEARCH,

    SHOPPING_CART,
    REMOVE_FROM_CART,
    BUY_SHOPPING_CART, // for server

    PURCHASE_HISTORY,

    EXPORT_HISTORY,

    STORE_STATS, // for server
    SORT_STORE_STATS, // for server

    CREATE_STORE,
    VIEW_PRODUCT_PAGE,
    TOTAL_PURCHASE_QUANTITIES,
    SORT_PRODUCTS,

    STORE_PAGE,
    STORE_SALES,
    LIST_SALES,
    IMPORT_CSV,
    EXPORT_CSV,

    CLOSE_CONNECTION, // for server, not switch
    ;
}
