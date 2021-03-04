package com.mystihgreeh.go4lunch.api.helper;

import retrofit2.http.Query;

public class MessageHelper {
    private static final String COLLECTION_NAME = "messages";

    // --- GET ---

    public static Query getAllMessageForChat(String chat){
        return (Query) ChatHelper.getChatCollection()
                .document(chat)
                .collection(COLLECTION_NAME)
                .orderBy("dateCreated")
                .limit(50);
    }
}
