package com.todolist.Backendproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.todolist.Backendproject.Todo.Priority;
import com.todolist.Backendproject.Todo.Todo;
import com.todolist.Backendproject.Todo.TodoRepository;

@SpringBootTest
class BackEndApplicationTests {

	@Autowired
	TodoRepository todoRepository = new TodoRepository();

	@Test
	void testGetById() {
		Todo todo1 = new Todo(1, "limpiar", false, null, null, null);
		todoRepository.add(todo1);
		Todo todo = todoRepository.findById(1);
		assert(todo.getId() == 1);
	}

	@Test
	void testGetByIdFailed() {
		Todo todo1 = new Todo(1, "limpiar", false, null, null, null);
		todoRepository.add(todo1);
		Todo todo = todoRepository.findById(43);
		assert(todo == null);
	}

	@Test
	void testFilterByName() {
		Todo todo1 = new Todo(1, "limpiar", false, null, null, null);
		Todo todo2 = new Todo(2, "limpiar la casa", false, null, null, null);
		Todo todo3 = new Todo(3, "limpiar el auto", false, null, null, null);
		Todo todo4 = new Todo(4, "Lavar", false, null, null, null);
		todoRepository.add(todo1);
		todoRepository.add(todo2);
		todoRepository.add(todo3);
		todoRepository.add(todo4);

		assert(todoRepository.filterByName("limpiar").size() == 3);
	}

	@Test
	void testFilterByPriority() {
		Todo todo1 = new Todo(0, "Hi", false, Priority.HIGH, null, null);
		Todo todo2 = new Todo(0, "Hi", false, Priority.MEDIUM, null, null);
		Todo todo3 = new Todo(0, "Hi", false, Priority.LOW, null, null);
		Todo todo4 = new Todo(0, "Hi", false, Priority.LOW, null, null);
		todoRepository.add(todo1);
		todoRepository.add(todo2);
		todoRepository.add(todo3);
		todoRepository.add(todo4);

		assert(todoRepository.filterByPriority(Priority.LOW).size() == 2);
	}

}
