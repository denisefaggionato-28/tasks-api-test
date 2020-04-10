package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass///executado uma vez quando inicia a classe, deve ser static porque não muda na executão depois de construído
	public static void setup() {
		RestAssured.baseURI="http://localhost:8001/tasks-backend";//configiração global
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")//focando do recurso
		.then()
			.statusCode(200)
			;
	}
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{\"task\": \"teste Via API\",\"dueDate\": \"2020-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
			;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{\"task\": \"teste Via API\",\"dueDate\": \"2010-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message",CoreMatchers.is("Due date must not be in past"));
			;
	}
	@Test
	public void deveRemoverTarefaComSucesso() {
		Integer id = RestAssured.given()
			.body("{\"task\": \"tarefa teste\",\"dueDate\": \"2020-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
			.extract().path("id")
			;
		System.out.println(id);
		RestAssured.given()
		.when()
			.delete("/todo/"+id)
		.then()
			.statusCode(204)
		;
		
	}

}



