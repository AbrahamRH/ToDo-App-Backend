package com.todolist.Backendproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.todolist.Backendproject.Component.Todo;
import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Service.TodoService;
import com.todolist.Backendproject.Repository.TodoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

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
	void testFilter() {
		service.createTodo(new Todo("Limpiar Casa", true, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Casa", false, Priority.HIGH, LocalDate.of(2023,03,2)));
		service.createTodo(new Todo("Limpiar Auto", true, Priority.MEDIUM, LocalDate.of(2023,02,2)));
		service.createTodo(new Todo("Limpiar Auto", false, Priority.LOW, null));
		service.createTodo(new Todo("Manejar Auto", true, Priority.LOW, LocalDate.of(2023,07,2)));
		service.createTodo(new Todo("Limpiar Auto", false, Priority.HIGH, LocalDate.of(2023,12,4)));
		service.filter("Limpiar", "LOW", "Done").forEach(System.out::println);
	}

	@Test
	void testAverage(){
		Todo todo1 = new Todo("Limpiar Casa", false, Priority.HIGH, LocalDate.of(2023,03,2));
		Todo todo2 = new Todo("Limpiar Casa", false, Priority.HIGH, LocalDate.of(2023,03,2));
		todo1.setDone(true);
		todo1.setDoneDate(LocalDateTime.of(2023, Month.MARCH, 17,11,30,2,00000));
		todo2.setDone(true);
		todo2.setDoneDate(LocalDateTime.of(2023, Month.MARCH, 17,11,30,2,00000));
		service.createTodo(todo1);
		service.createTodo(todo2);


		Long seconds = service.average();
		long sec = seconds % 60;
    long minutes = seconds % 3600 / 60;
    long hours = seconds % 86400 / 3600;
    long days = seconds / 86400;

    System.out.println("Day " + days + " Hour " + hours + " Minute " + minutes + " Seconds " + sec);


	}

}
