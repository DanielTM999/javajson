### JSON (Conversor de Objetos Java para JSON)

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
