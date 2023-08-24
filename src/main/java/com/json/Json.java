package com.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

    public Json CreateComplexJSON(List<Map<String, Object>> itens, String id){
        Json json = new Json();
        List<String> jsonList = new ArrayList<>();

        for(Map<String, ?> obj : itens){
            Json internalJson = new Json();
            for(Map.Entry<String, ?> entry : obj.entrySet()){
                internalJson.put(entry.getKey(), entry.getValue());
            }
            jsonList.add(internalJson.toJson());
        }


        json.put(id, jsonList);

        return json;
    }


    public Json CreateComplexJSON(Collection<?> itens){
        Object PrimeiroIten = itens.iterator().next();

        Class<?> clazz = PrimeiroIten.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Method[] Allmetodos = clazz.getDeclaredMethods();

        List<Method> Getter = new ArrayList<>();
        List<String> var = new ArrayList<>();

        List<Map<String, Object>> finalObject = new ArrayList<>();

        for (int i = 0; i < Allmetodos.length; i++) {
            String methodName = Allmetodos[i].getName().toLowerCase();
            for(Field variable : fields){
                String target = "get"+variable.getName().toLowerCase();
                if(methodName.equals(target) && !java.lang.reflect.Modifier.isStatic(variable.getModifiers())){
                    Getter.add(Allmetodos[i]);
                    var.add(variable.getName());
                }
            }
        }

        for(Object obj : itens){
            Map<String, Object> instanceMap = new HashMap<>();

            for (int i = 0; i < Getter.size(); i++){
                String varName = var.get(i);
                Method getter = Getter.get(i);

                try {
                    Object valor = getter.invoke(obj);
                    instanceMap.put(varName, valor);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            finalObject.add(instanceMap);
        }

        return CreateComplexJSON(finalObject, clazz.getSimpleName());
    }

    public Json CreateComplexJSON(Collection<?> itens, String ItentificadorJson){
        Object PrimeiroIten = itens.iterator().next();

        Class<?> clazz = PrimeiroIten.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Method[] Allmetodos = clazz.getDeclaredMethods();

        List<Method> Getter = new ArrayList<>();
        List<String> var = new ArrayList<>();

        List<Map<String, Object>> finalObject = new ArrayList<>();

        for (int i = 0; i < Allmetodos.length; i++) {
            String methodName = Allmetodos[i].getName().toLowerCase();
            for(Field variable : fields){
                String target = "get"+variable.getName().toLowerCase();
                if(methodName.equals(target) && !java.lang.reflect.Modifier.isStatic(variable.getModifiers())){
                    Getter.add(Allmetodos[i]);
                    var.add(variable.getName());
                }
            }
        }

        for(Object obj : itens){
            Map<String, Object> instanceMap = new HashMap<>();

            for (int i = 0; i < Getter.size(); i++){
                String varName = var.get(i);
                Method getter = Getter.get(i);

                try {
                    Object valor = getter.invoke(obj);
                    instanceMap.put(varName, valor);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            finalObject.add(instanceMap);
        }

        if(ItentificadorJson == null ||ItentificadorJson.isEmpty() || ItentificadorJson.isBlank()){
            ItentificadorJson = clazz.getSimpleName();
        }

        return CreateComplexJSON(finalObject, ItentificadorJson);
    }

    public Json CreateComplexJSON(Object obj){
        Json json = new Json();

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();

        List<Field> varNames = new ArrayList<>();
        List<Method> Getter = new ArrayList<>();

        for(Method method : methods){
            String methodName = method.getName().toLowerCase();
            for(Field name : fields){
                String target = "get"+name.getName().toLowerCase();
                if (methodName.equals(target) && !java.lang.reflect.Modifier.isStatic(name.getModifiers())) {
                    varNames.add(name);
                    Getter.add(method);
                }
            }
        }

        for(int i = 0; i < varNames.size(); i++){
            Method getter = Getter.get(i);

            try {
                Object value = getter.invoke(obj);

                if(varNames.get(i).getType().getSimpleName().equalsIgnoreCase("List") || varNames.get(i).getType().getSimpleName().equalsIgnoreCase("ArrayList")){
                    List<?> lista = (List<?>) value;
                    json.put(varNames.get(i).getName(), json.CreateComplexJSON(lista).toJson());
                }else{
                    json.put(varNames.get(i).getName(), value);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  json;
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



    @Override
    public String toString() {
        return toJson();
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
