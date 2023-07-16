package com.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonAnalyzer {
    private String json;

    public JsonAnalyzer(String json) {
        this.json = json;
    }

    public Map<String, ?> parse() {
        return parseObject();
    }


    public List<Map<String, Object>> analyzeOutput(String output) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        output = output.replace("[", "").replace("]", "");

        String[] objects = output.split("\\},\\s*\\{");

        for (String object : objects) {

            object = object.replace("{", "").replace("}", "");


            String[] pairs = object.split(",\\s*");

            Map<String, Object> resultMap = new HashMap<>();
            for (String pair : pairs) {

                String[] keyValue = pair.split("=");

                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                // Remova as aspas em torno do valor, se existirem
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }

                resultMap.put(key, value);
            }

            resultList.add(resultMap);
        }

        return resultList;
    }

    private Object parseValue() {
        char currentChar = getNextNonWhitespaceChar();
        if (currentChar == '{') {
            return parseObject();
        } else if (currentChar == '[') {
            return parseArray();
        } else if (currentChar == '"') {
            return parseString();
        } else if (Character.isDigit(currentChar) || currentChar == '-') {
            return parseNumber();
        } else if (currentChar == 't' || currentChar == 'f') {
            return parseBoolean();
        } else if (currentChar == 'n') {
            return parseNull();
        } else {
            throw new IllegalArgumentException("Invalid JSON");
        }
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> object = new HashMap<>();
        consumeChar('{');
        while (true) {
            consumeWhitespace();
            if (peekChar() == '}') {
                consumeChar('}');
                break;
            }
            String key = parseString();
            consumeWhitespace();
            consumeChar(':');
            Object value = parseValue();
            object.put(key, value);
            consumeWhitespace();
            if (peekChar() == ',') {
                consumeChar(',');
            } else if (peekChar() == '}') {
                consumeChar('}');
                break;
            } else {
                throw new IllegalArgumentException("Invalid JSON");
            }
        }
        return object;
    }

    private List<Object> parseArray() {
        List<Object> array = new ArrayList<>();
        consumeChar('[');
        while (true) {
            consumeWhitespace();
            if (peekChar() == ']') {
                consumeChar(']');
                break;
            }
            Object value = parseValue();
            array.add(value);
            consumeWhitespace();
            if (peekChar() == ',') {
                consumeChar(',');
            } else if (peekChar() == ']') {
                consumeChar(']');
                break;
            } else {
                throw new IllegalArgumentException("Invalid JSON");
            }
        }
        return array;
    }

    private String parseString() {
        StringBuilder sb = new StringBuilder();
        consumeChar('"');
        while (peekChar() != '"') {
            char currentChar = consumeChar();
            if (currentChar == '\\') {
                char escapedChar = consumeChar();
                if (escapedChar == 'b') {
                    sb.append('\b');
                } else if (escapedChar == 'f') {
                    sb.append('\f');
                } else if (escapedChar == 'n') {
                    sb.append('\n');
                } else if (escapedChar == 'r') {
                    sb.append('\r');
                } else if (escapedChar == 't') {
                    sb.append('\t');
                } else if (escapedChar == '"') {
                    sb.append('"');
                } else if (escapedChar == '\\') {
                    sb.append('\\');
                } else {
                    throw new IllegalArgumentException("Invalid JSON");
                }
            } else {
                sb.append(currentChar);
            }
        }
        consumeChar('"');
        return sb.toString();
    }

    private Number parseNumber() {
        StringBuilder sb = new StringBuilder();
        char currentChar = peekChar();
        while (Character.isDigit(currentChar) || currentChar == '-' || currentChar == '.' || currentChar == 'e' || currentChar == 'E') {
            sb.append(consumeChar());
            currentChar = peekChar();
        }
        String numberStr = sb.toString();
        if (numberStr.contains(".") || numberStr.toLowerCase().contains("e")) {
            return Double.parseDouble(numberStr);
        } else {
            return Long.parseLong(numberStr);
        }
    }

    private Boolean parseBoolean() {
        String boolStr = consumeString(4);
        if (boolStr.equals("true")) {
            return true;
        } else if (boolStr.equals("false")) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid JSON");
        }
    }

    private Object parseNull() {
        String nullStr = consumeString(4);
        if (nullStr.equals("null")) {
            return null;
        } else {
            throw new IllegalArgumentException("Invalid JSON");
        }
    }

    private char consumeChar() {
        if (json.isEmpty()) {
            throw new IllegalArgumentException("Invalid JSON");
        }
        char currentChar = json.charAt(0);
        json = json.substring(1);
        return currentChar;
    }

    private void consumeChar(char expectedChar) {
        char currentChar = consumeChar();
        if (currentChar != expectedChar) {
            throw new IllegalArgumentException("Invalid JSON");
        }
    }

    private String consumeString(int length) {
        if (json.length() < length) {
            throw new IllegalArgumentException("Invalid JSON");
        }
        String consumedString = json.substring(0, length);
        json = json.substring(length);
        return consumedString;
    }

    private char peekChar() {
        if (json.isEmpty()) {
            throw new IllegalArgumentException("Invalid JSON");
        }
        return json.charAt(0);
    }

    private char getNextNonWhitespaceChar() {
        consumeWhitespace();
        if (json.isEmpty()) {
            throw new IllegalArgumentException("Invalid JSON");
        }
        return json.charAt(0);
    }

    private void consumeWhitespace() {
        while (!json.isEmpty() && Character.isWhitespace(json.charAt(0))) {
            json = json.substring(1);
        }
    }

}
