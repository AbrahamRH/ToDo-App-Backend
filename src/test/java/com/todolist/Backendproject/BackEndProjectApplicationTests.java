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
	void testGetById() {
		service.createTodo(new Todo(1,"Limpiar", false, Priority.HIGH, null, null));
		service.createTodo(new Todo(2,"Limpiar", false, Priority.HIGH, null, null));
		service.createTodo(new Todo(3,"Limpiar", false, Priority.HIGH, null, null));
		assert(service.findById(1).getId() == 1);
	}

	@Test
	void testFilterByName() {
		service.createTodo(new Todo(1,"Limpiar", false, Priority.HIGH, null, null));
		service.createTodo(new Todo(2,"Limpiar Casa", false, Priority.HIGH, null, null));
		service.createTodo(new Todo(3,"Limpiar Auto", false, Priority.HIGH, null, null));
		service.createTodo(new Todo(4,"Lavar Patio", false, Priority.HIGH, null, null));
		assert(service.filterByName("limpiar").size() == 3);
	}

	@Test
	void testFilterByPriority() {
		service.createTodo(new Todo(1,"Limpiar", false, Priority.LOW, null, null));
		service.createTodo(new Todo(2,"Limpiar Casa", false, Priority.HIGH, null, null));
		service.createTodo(new Todo(3,"Limpiar Auto", false, Priority.MEDIUM, null, null));
		service.createTodo(new Todo(4,"Lavar Patio", false, Priority.HIGH, null, null));
		assert(service.filterByPriority(Priority.HIGH).size() == 2);
	}

	@Test 
	void testFilterByDone() {
		service.createTodo(new Todo(1,"Limpiar", false, Priority.LOW, null, null));
		service.createTodo(new Todo(2,"Limpiar Casa", false, Priority.HIGH, null, null));
		service.createTodo(new Todo(3,"Limpiar Auto", true, Priority.MEDIUM, null, null));
		assert(service.filterByDone(false).size() == 2);
	}

}
