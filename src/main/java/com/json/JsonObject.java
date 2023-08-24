package com.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface JsonObject {
    String toJson();

    void put(String id, Object value);

    Json CreateComplexJSON(List<Map<String, Object>> itens, String id);

    Json CreateComplexJSON(Collection<?> itens);

    Json CreateComplexJSON(Collection<?> itens, String ItentificadorJson);

    Json CreateComplexJSON(Object obj);
}
