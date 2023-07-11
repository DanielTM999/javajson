package com.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonLexicalAnalyzer {
    private String jsonBase;
    private int position;
    private List<String> tokenst= new ArrayList<>();
    Map<String, String> mapa = new HashMap<>();

    public JsonLexicalAnalyzer(String jsonBase) {
        this.jsonBase = jsonBase;
        this.position = 0;
    }

    public String GetDados(String valor) {
    	return mapa.get(valor);
    }

    public void analyze() {
        while (position < jsonBase.length()) {
            char currentChar = jsonBase.charAt(position);
            if ( currentChar == '\\' || currentChar == '{' || currentChar == '}') {

            	position++;
            	continue;
            }

            if (Character.isLetterOrDigit(currentChar)) {
                String identifier = extractIdentifier();
                tokenst.add(identifier);
            }

            position++;
        }

        mapa = ordenar(tokenst);
    }

    private String extractIdentifier() {
        StringBuilder identifier = new StringBuilder();
        char currentChar = jsonBase.charAt(position);
        while (Character.isLetterOrDigit(currentChar) || Character.isWhitespace(currentChar)) {
            identifier.append(currentChar);
            position++;
            if (position >= jsonBase.length())
                break;
            currentChar = jsonBase.charAt(position);
        }
        return identifier.toString();
    }

    private Map<String, String> ordenar(List<String> tokens) {
    	Map<String, String> mapa = new HashMap<>();
    	List<String> key = new ArrayList<>();
    	List<String> value = new ArrayList<>();
    	System.out.println();
    	for(int i = 0; i < (tokens.size() ); i++) {
    		if(i % 2 == 0) {
    			key.add(tokens.get(i));
    		}else {
    			value.add(tokens.get(i));
    		}
    	}

    	for (int i = 0; i < key.size(); i++) {
			mapa.put(key.get(i), value.get(i));
		}

    	return mapa;
    }
}

