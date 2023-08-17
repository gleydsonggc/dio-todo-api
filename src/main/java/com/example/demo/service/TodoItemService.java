package com.example.demo.service;

import com.example.demo.domain.TodoItem;

import java.util.List;

public interface TodoItemService {

	TodoItem save(TodoItem todoItem);

	TodoItem findById(Long id);

	List<TodoItem> findAll();

	TodoItem update(Long id, TodoItem todoItemToUpdate);

	void delete(Long id);

}
