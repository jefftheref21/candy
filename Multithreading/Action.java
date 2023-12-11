public enum Action {
    UPDATE_CANDY_MANAGER,

    BUYER,
    SELLER,
    LOGIN,

    INVALID_CREDENTIALS,
    VALID_CREDENTIALS_BUYER,
    VALID_CREDENTIALS_SELLER,

    CANDY_PAGE,
    BUY_INSTANTLY, // for server
    INVALID_QUANTITY,
    ADD_TO_CART,
    BACK,

    SEARCH,

    SHOPPING_CART,
    REMOVE_FROM_CART,
    BUY_SHOPPING_CART, // for server
    VIEW_SHOPPING_CARTS,

    BUY_SUCCESSFUL,
    BUY_QUANTITY_EXCEEDS,
    BUY_QUANTITY_INVALID,

    PURCHASE_HISTORY,

    EXPORT_HISTORY,
    EXPORT_HISTORY_SUCCESSFUL,
    EXPORT_HISTORY_UNSUCCESSFUL,

    STORE_STATS, // for server
    SORT_STORE_STATS, // for server

    CREATE_STORE,
    VIEW_PRODUCT_PAGE,
    TOTAL_PURCHASE_QUANTITIES,
    SORT_PRODUCTS,

    CREATE_STORE_SUCCESSFUL,
    CREATE_STORE_UNSUCCESSFUL,
    EDIT_STORE,
    EDIT_STORE_SUCCESSFUL,
    EDIT_STORE_UNSUCCESSFUL,

    GET_CANDY_ID,
    ADD_CANDY,
    EDIT_CANDY,
    DELETE_CANDY,

    ADD_CANDY_SUCCESSFUL,
    ADD_CANDY_UNSUCCESSFUL,
    EDIT_CANDY_SUCCESSFUL,
    EDIT_CANDY_UNSUCCESSFUL,
    DELETE_CANDY_SUCCESSFUL,
    DELETE_CANDY_UNSUCCESSFUL,

    STORE_PAGE,
    STORE_SALES,
    LIST_SALES,


    IMPORT_CSV,
    EXPORT_CSV,

    IMPORT_SUCCESSFUL,
    IMPORT_UNSUCCESSFUL,
    EXPORT_SUCCESSFUL,
    EXPORT_UNSUCCESSFUL,

    CLOSE_CONNECTION, // for server, not switch
    ;
}
