package com.example.demo.service.impl;

import com.example.demo.domain.TodoItem;
import com.example.demo.repository.TodoItemRepository;
import com.example.demo.service.TodoItemService;
import com.example.demo.service.exception.BusinessException;
import com.example.demo.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class TodoItemServiceImpl implements TodoItemService {

	private final TodoItemRepository todoItemRepository;

	@Override
	public TodoItem save(TodoItem todoItem) {
		if (null != todoItem.getId() && todoItemRepository.existsById(todoItem.getId())) {
			throw new BusinessException("A TodoItem with this ID already exists.");
		}
		return todoItemRepository.save(todoItem);
	}

	@Override
	public TodoItem findById(Long id) {
		return todoItemRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	public List<TodoItem> findAll() {
		return todoItemRepository.findAll();
	}

	@Override
	public TodoItem update(Long id, TodoItem todoItemToUpdate) {
		TodoItem managedTodoItem = findById(id);
		managedTodoItem.setTitle(todoItemToUpdate.getTitle());
		managedTodoItem.setDescription(todoItemToUpdate.getDescription());
		managedTodoItem.setDone(todoItemToUpdate.isDone());
		return todoItemRepository.save(managedTodoItem);
	}

	@Override
	public void delete(Long id) {
		todoItemRepository.delete(findById(id));
	}

}
