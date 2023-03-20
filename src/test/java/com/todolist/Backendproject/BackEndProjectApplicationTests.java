package com.todolist.Backendproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.todolist.Backendproject.Component.Todo;
import com.todolist.Backendproject.Component.Priority;
import com.todolist.Backendproject.Service.TodoService;
import com.todolist.Backendproject.Repository.TodoRepository;

import java.util.ArrayList;
import java.util.List;
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

	@Test
	void testSortingByPriority(){
		service.createTodo(new Todo("Limpiar ", true, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Bano", false, Priority.HIGH, LocalDate.of(2023,03,2)));
		service.createTodo(new Todo("Limpiar Cuarto", true, Priority.MEDIUM, LocalDate.of(2023,02,2)));
		service.createTodo(new Todo("Limpiar moto", false, Priority.LOW, null));
		service.createTodo(new Todo("Manejar", true, Priority.LOW, LocalDate.of(2023,07,2)));
		service.createTodo(new Todo("Hacer tarea", false, Priority.HIGH, LocalDate.of(2023,12,4)));
		service.createTodo(new Todo("Leer", false, Priority.MEDIUM, LocalDate.of(2023,12,4)));
    List<Todo> list = List.copyOf(service.findAll());
    List<Todo> todos = new ArrayList<>(list);
		String[] params = {"priority", "asc"};
		System.out.println("\n-- Priority Ascending -- \n");
		service.sort(todos, params).forEach(System.out::println);
		params[1] = "desc";
		System.out.println("\n-- Priority descending -- \n");
		service.sort(todos, params).forEach(System.out::println);
	}

	@Test
	void testSortingByDueDate(){
		service.createTodo(new Todo("Limpiar ", true, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Bano", false, Priority.HIGH, LocalDate.of(2023,03,2)));
		service.createTodo(new Todo("Limpiar Cuarto", true, Priority.MEDIUM, LocalDate.of(2023,02,2)));
		service.createTodo(new Todo("Limpiar moto", false, Priority.LOW, null));
		service.createTodo(new Todo("Manejar", true, Priority.LOW, LocalDate.of(2023,07,2)));
		service.createTodo(new Todo("Hacer tarea", false, Priority.HIGH, LocalDate.of(2023,12,4)));
		service.createTodo(new Todo("Leer", false, Priority.MEDIUM, LocalDate.of(2023,12,4)));
    List<Todo> list = List.copyOf(service.findAll());
    List<Todo> todos = new ArrayList<>(list);
		String[] params = {"dueDate", "asc"};
		System.out.println("Due date Ascending");
		service.sort(todos, params).forEach(System.out::println);
		params[1] = "desc";
		System.out.println("Due date Descending");
		service.sort(todos, params).forEach(System.out::println);
	}

	@Test
	void testSortingByPriorityAndDueDate(){
		service.createTodo(new Todo("Limpiar ", true, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Bano", false, Priority.HIGH, LocalDate.of(2023,03,2)));
		service.createTodo(new Todo("Limpiar Cuarto", true, Priority.MEDIUM, LocalDate.of(2023,02,2)));
		service.createTodo(new Todo("Limpiar moto", false, Priority.LOW, null));
		service.createTodo(new Todo("Manejar", true, Priority.LOW, LocalDate.of(2023,07,2)));
		service.createTodo(new Todo("Hacer tarea", false, Priority.HIGH, LocalDate.of(2023,12,4)));
		service.createTodo(new Todo("Leer", false, Priority.MEDIUM, LocalDate.of(2023,12,4)));
		service.createTodo(new Todo("Limpiar moto", false, Priority.LOW, null));
    List<Todo> list = List.copyOf(service.findAll());
    List<Todo> todos = new ArrayList<>(list);
		System.out.println(" -- First by dueDate -- ");
		String[] params = {"dueDate,asc", "priority,asc"};
		System.out.println("\n -- Due date Ascending - Priority Ascending -- \n");
		service.sort(todos, params).forEach(todo -> System.out.println(todo.getId() + " " + todo.getPriority().toString() + " " + todo.getDueDate()));
		params[0] = "dueDate,desc";
		System.out.println("\n -- Due date Descending - Priority Ascending -- \n");
		service.sort(todos, params).forEach(todo -> System.out.println(todo.getId() + " " + todo.getPriority().toString() + " " + todo.getDueDate()));
		params[0] = "priority,desc";
		params[1] = "dueDate,asc";
		System.out.println("\n -- Priority Descending - Due datae Ascending -- \n");
		service.sort(todos, params).forEach(todo -> System.out.println(todo.getId() + " " + todo.getPriority().toString() + " " + todo.getDueDate()));
		String[] params2 = {"priotiy,desc"};
		System.out.println("\n -- Priority Descending -- \n");
		service.sort(todos, params2).forEach(todo -> System.out.println(todo.getId() + " " + todo.getPriority().toString() + " " + todo.getDueDate()));

	}

	@Test
	void testSortingByPriorityAndDueDate2(){
		service.createTodo(new Todo("Limpiar ", true, Priority.HIGH, null));
		service.createTodo(new Todo("Limpiar Bano", false, Priority.HIGH, LocalDate.of(2023,03,2)));
		service.createTodo(new Todo("Limpiar moto", false, Priority.MEDIUM, null));
		service.createTodo(new Todo("Limpiar Cuarto", true, Priority.MEDIUM, LocalDate.of(2023,02,2)));
		service.createTodo(new Todo("Limpiar moto", false, Priority.LOW, null));
		service.createTodo(new Todo("Manejar", true, Priority.LOW, LocalDate.of(2023,07,2)));
		service.createTodo(new Todo("Hacer tarea", false, Priority.HIGH, LocalDate.of(2023,12,4)));
		service.createTodo(new Todo("Leer", false, Priority.MEDIUM, LocalDate.of(2023,12,4)));
		service.createTodo(new Todo("Limpiar moto", false, Priority.LOW, null));
    List<Todo> list = List.copyOf(service.findAll());
    List<Todo> todos = new ArrayList<>(list);
		String[] params = {"priority,desc", "dueDate,asc"};
		System.out.println("\n -- Priority Descending - Due datae Ascending -- \n");
		service.sort(todos, params).forEach(todo -> System.out.println(todo.getId() + " " + todo.getPriority().toString() + " " + todo.getDueDate()));

	}

}
