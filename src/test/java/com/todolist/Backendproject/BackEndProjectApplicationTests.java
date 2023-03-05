package com.todolist.Backendproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Component.Todo;
import com.todolist.Backendproject.Service.TodoService;
import com.todolist.Backendproject.Repository.TodoRepository;

@SpringBootTest
class BackEndProjectApplicationTests {

	TodoRepository todoRepository = new TodoRepository();
	TodoService service = new TodoService(todoRepository);

	@Test
	void testCreateAndPrintTodo(){
		service.createTodo(new Todo("Limpiar", false, Priority.HIGH, null));
		service.createTodo(new Todo("Hacer compras", false, Priority.HIGH, null));
		service.createTodo(new Todo("Crear contenido", false, Priority.HIGH, null));

		service.findAll().forEach(
			todo -> System.out.println(todo)
		);
	}

	@Test
	void testGetById() {
		Todo newTodo = new Todo("Limpiar", false, Priority.HIGH, null);

		service.createTodo(newTodo);
		service.createTodo(new Todo("Limpiar", false, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar", false, Priority.HIGH, null));
		assert(service.findById(newTodo.getId()).getId() == newTodo.getId());
	}

	@Test
	void testFilterByName() {
		service.createTodo(new Todo("Limpiar", false, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Casa", false, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Auto", false, Priority.HIGH, null));
		service.createTodo(new Todo("Lavar Patio", false, Priority.HIGH, null));
		assert(service.filterByName("limpiar").size() == 3);
	}

	@Test
	void testFilterByPriority() {
		service.createTodo(new Todo("Limpiar", false, Priority.LOW, null));
		service.createTodo(new Todo("Limpiar Casa", false, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Auto", false, Priority.MEDIUM, null));
		service.createTodo(new Todo("Lavar Patio", false, Priority.HIGH, null));
		assert(service.filterByPriority(Priority.HIGH).size() == 2);
	}

	@Test 
	void testFilterByDone() {
		service.createTodo(new Todo("Limpiar", false, Priority.LOW, null));
		service.createTodo(new Todo("Limpiar Casa", false, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Auto", true, Priority.MEDIUM, null));
		assert(service.filterByDone(false).size() == 2);
	}

	@Test
	void testDeleteTodo(){
		Todo newTodo = new Todo("Limpiar", false, Priority.LOW, null);
		service.createTodo(newTodo);
		service.createTodo(new Todo("Limpiar Casa", false, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Auto", true, Priority.MEDIUM, null));

		assert(service.delete(newTodo.getId()) == true);
	}

	@Test
	void testUpdateTodo(){
		Todo newTodo = new Todo("Limpiar", false, Priority.LOW, null);
		service.createTodo(newTodo);
		assert(service.update(newTodo.getId(),"Limpiar Casa", Priority.HIGH, null) == true);
		assert(service.findById(newTodo.getId()).getName() == "Limpiar Casa");
		assert(service.findById(newTodo.getId()).getPriority() == Priority.HIGH);
	}

	@Test
	void testSortingPriority(){
		Todo newTodo = new Todo("Limpiar", false, Priority.LOW, null);
		service.createTodo(newTodo);
		service.createTodo(new Todo("Limpiar Casa", false, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Auto", true, Priority.MEDIUM, null));
		service.sort().forEach(System.out::println);
	}

}
