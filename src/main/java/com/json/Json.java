package com.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Json{
    private List<String> id = new ArrayList<>();
    private List<Object> obj = new ArrayList<>();
    private List<String> convert = new ArrayList<>();
    private String json = "";

    public Json(){
        super();
    }

    public Json(ArrayList<String> id, ArrayList<Object> obj){
        this.id = id;
        this.obj = obj;
    }

    public Json(JsonBuilder build){
        this.id = build.id;
        this.obj = build.obj;
    }

    public String toJson(){
        try {
            Converter();
            add();
            format();
            return json;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public void put(String id, Object value){
        this.id.add(id);
        this.obj.add(value);
    }

    public Json CreateComplexJSON(Map<String, ?> obj, String id){
        Json json = new Json();
        List<String> jsonList = new ArrayList<>();

        for(Map.Entry<String, ?> entry : obj.entrySet()){
            Json internalJson = new Json();
            internalJson.put(entry.getKey(), entry.getValue());
            jsonList.add(internalJson.toJson());
        }

        json.put(id, jsonList);

        return json;
    }

    private void Converter() throws Exception{
        if(id.size() != obj.size()){
            throw new Exception("ArrayList.size() diferantes");
        }
        String frase;
        for (int i = 0; i < obj.size(); i++) {
            String aux;
            if(obj.get(i) != null){
                aux = obj.get(i).toString();
            }else{
                aux = "";
            }

            try {
                int aux1 = Integer.parseInt(aux);
                frase = "\"" + id.get(i) + "\":" + aux1;
                convert.add(frase);
            } catch (Exception e) {
                if(aux.equalsIgnoreCase("true") || aux.equalsIgnoreCase("false")){
                    frase = "\"" + id.get(i) + "\":" + obj.get(i);
                    convert.add(frase);
                }else if(find(obj.get(i))){
                    frase = "\""+id.get(i)+"\":"+obj.get(i);
                    convert.add(frase);
                }else{
                    frase = "\""+id.get(i)+"\":\""+obj.get(i)+"\"";
                    convert.add(frase);
                }
            }

        }
    }

    private void add(){
        for (int i = 0; i < convert.size(); i++) {
            if(i == convert.size() - 1){
                this.json += convert.get(i);
            }else{
                this.json += convert.get(i) + ",";
            }
        }
    }

    private void format(){
        json = "{"+json+"}";
    }

    private boolean find(Object object){
        if(object == null){
            object = "";
        }
        String frase = object.toString();
        for(int i = 0; i < frase.length(); i++){
            char letter = frase.charAt(i);
            if(letter == '[' || letter == ']'){
                return true;
            }
        }

        return false;
    }

    public static class JsonBuilder{
        private ArrayList<String> id = new ArrayList<>();
        private ArrayList<Object> obj = new ArrayList<>();

        public JsonBuilder param(String param){
            id.add(param);
            return this;
        }

        public JsonBuilder Value(Object value){
            this.obj.add(value);
            return this;
        }

        public JsonBuilder Value(Boolean value){
            this.obj.add(value);
            return this;
        }

        public JsonBuilder Value(ArrayList<Object> value){
            this.obj = value;
            return this;
        }

        public Json Create(){
            return new Json(this);
        }
    }
}
