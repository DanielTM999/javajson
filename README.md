# JSON (Conversor de Objetos Java para JSON)

Esta classe fornece uma maneira simples e conveniente de converter objetos Java para o formato JSON. Ela permite que você crie um objeto JSON especificando as chaves e os valores correspondentes, e depois o converta para uma representação em string JSON.
Uso

Instancie a classe Json com um construtor vazio ou use o JsonBuilder para uma maneira mais conveniente.
Adicione pares de chave-valor ao objeto JSON usando o método put.
Chame o método toJson para converter o objeto JSON em uma string JSON.

## Exemplo:

```java
    Json json = new Json();
    json.put("nome", "Fulano de Tal");
    json.put("idade", 30);
    String jsonString = json.toJson();
    System.out.println(jsonString); // Saída: {"nome":"Fulano de Tal","idade":30}
```

## Métodos disponíveis

<ul>
    <li>public void put(String id, Object value): Adiciona um par de chave-valor ao objeto JSON.</li>
    <li>public String toJson(): Converte o objeto JSON em uma string JSON.</li>
</ul>

## Classe interna

JsonBuilder: Uma classe de construtor para simplificar o processo de criação de objetos JSON.

## Exemplo usando o construtor:

```java

Json json = new Json.JsonBuilder()
                .param("nome").Value("Fulano de Tal")
                .param("idade").Value(30)
                .Create();
String jsonString = json.toJson();
System.out.println(jsonString); // Saída: {"nome":"Fulano de Tal","idade":30}
```

Observação: Esta classe não fornece funcionalidade de análise JSON (converter uma string JSON para objetos Java).

# classe para Analizar Json

A classe JsonAnalyzer é responsável por analisar e converter uma string JSON em uma estrutura de dados Java composta por Map<String, ?>, List<Map<String, Object>> e outros tipos de dados primitivos.

## Métodos disponíveis

<ul>
    <li>publicMap<String, ?> parse(): Esse método é responsável por iniciar o processo de análise do JSON fornecido no construtor. Ele retorna um Map que representa o objeto JSON de nível superior.</li>
    <li>public List<Map<String, Object>> analyzeOutput(String outpu):Esse método é utilizado para analisar uma saída JSON em formato de string. Ele recebe a string output contendo o JSON a ser analisado e retorna uma lista de Map<String, Object>. Cada elemento da lista representa um objeto JSON no formato chave-valor.</li>
</ul>

## exemplos Json simples

```java
    public static void main(String[] args) {
        String jsonString = "{\"nome\":\"Daniel\",\"idade\":19,\"cidade\":\"salvador\"}";
        JsonAnalyzer analyzer = new JsonAnalyzer(jsonString);
        Map<String, ?> jsonMap = analyzer.parse();

        System.out.println(jsonMap.get("nome"));
        System.out.println(jsonMap.get("idade"));
        System.out.println(jsonMap.get("cidade"));

    }

```


## exemplos Json complexo

```java
    public static void main(String[] args) {
        String jsonString = "{\"nome\":\"Daniel\",\"idade\":19,\"carros\":[{\"modelo\":\"BMW\",\"ano\":2021},{\"modelo\":\"Audi\",\"ano\":2022}],\"cidade\":\"salvador\"}";
        JsonAnalyzer analyzer = new JsonAnalyzer(jsonString);
        Map<String, ?> jsonMap = analyzer.parse();
        String output = jsonMap.get("carros").toString();
        List<Map<String, Object>> resultList = analyzer.analyzeOutput(output);

        for (Map<String, Object> resultMap : resultList) {
            System.out.println("Object:");
            for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key + ": " + value);
            }
            System.out.println();
        }
    }
```


## passo a passo(uso)

## Instancie a classe JsonAnalyzer, passando a string JSON no construtor:

```java
    String jsonString = "{\"nome\":\"Daniel\",\"idade\":19,\"carros\":[{\"modelo\":\"BMW\",\"ano\":2021},{\"modelo\":\"Audi\",\"ano\":2022}],\"cidade\":\"salvador\"}";

    JsonAnalyzer analyzer = new JsonAnalyzer(jsonString);

```

## Chame o método parse() para obter o resultado da análise:

```java
    Map<String, ?> result = analyzer.parse();
```

O resultado será um Map<String, ?> representando o objeto JSON de nível superior.

## Alternativamente, você pode usar o método analyzeOutput() para analisar uma saída JSON em formato de string (OBS para saidas de Json Superior EX: {}, []):

```java
    String output = jsonMap.get("carros").toString();//[{ano=2021, modelo=BMW}, {ano=2022, modelo=Audi}]
    List<Map<String, Object>> resultList = analyzer.analyzeOutput(output);
```

O resultado será uma lista de Map<String, Object>, onde cada elemento representa um objeto JSON no formato chave-valor.


## Exceções

A classe JsonAnalyzer pode lançar as seguintes exceções:

<ul>
    <li>IllegalArgumentException: Lançada quando a string JSON é inválida ou não está em conformidade com o formato esperado.</li>
</ul>

Certifique-se de tratar essas exceções de forma adequada ao utilizar a classe JsonAnalyzer.


## Considerações finais

A classe JsonAnalyzer fornece uma implementação simples para análise de JSON em Java. Ela suporta objetos JSON de nível superior, arrays JSON e os seguintes tipos de dados: strings, números, booleanos e valores nulos. Você pode incorporar essa classe em seu projeto para realizar análise e conversão de dados JSON de forma conveniente.
