package com.example.demo.controller;

import com.example.demo.controller.dto.TodoItemDTO;
import com.example.demo.domain.TodoItem;
import com.example.demo.mapper.TodoItemMapper;
import com.example.demo.service.TodoItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "TodoItem Controller", description = "TodoItem API for manage todo items")
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController
public class TodoItemController {

	private final TodoItemService todoItemService;

	private final TodoItemMapper todoItemMapper;

	@Operation(summary = "Get all todo items", description = "Retrieve a list of all todo items")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operation successful")
	})
	@GetMapping
	public ResponseEntity<List<TodoItemDTO>> findAll() {
		return ResponseEntity.ok(
				todoItemService.findAll().stream()
						.map(todoItemMapper::toDto)
						.collect(Collectors.toList())
		);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a todo item by ID", description = "Retrieve a specific todo item based on its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operation successful"),
			@ApiResponse(responseCode = "404", description = "Todo item not found")
	})
	public ResponseEntity<TodoItemDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(todoItemMapper.toDto(todoItemService.findById(id)));
	}

	@PostMapping
	@Operation(summary = "Create a new todo item", description = "Create a new todo item and return the created todo item's data")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Todo item created successfully"),
			@ApiResponse(responseCode = "422", description = "Invalid todo item data provided")
	})
	public ResponseEntity<TodoItemDTO> create(@RequestBody TodoItemDTO todoItemDTO) {
		TodoItem todoItem = todoItemMapper.toModel(todoItemDTO);
		TodoItem savedTodoItem = todoItemService.save(todoItem);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedTodoItem.getId())
				.toUri();
		return ResponseEntity.created(location).body(todoItemMapper.toDto(savedTodoItem));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a todo item", description = "Update the data of an existing todo item based on its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Todo item updated successfully"),
			@ApiResponse(responseCode = "404", description = "Todo item not found"),
			@ApiResponse(responseCode = "422", description = "Invalid todo item data provided")
	})
	public ResponseEntity<TodoItemDTO> update(@PathVariable Long id, @RequestBody TodoItemDTO todoItemDTO) {
		TodoItem updatedTodoItem = todoItemService.update(id, todoItemMapper.toModel(todoItemDTO));
		return ResponseEntity.ok(todoItemMapper.toDto(updatedTodoItem));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a todo item by ID", description = "Delete an existing todo item based on its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Todo item deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Todo item not found")
	})
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		todoItemService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
