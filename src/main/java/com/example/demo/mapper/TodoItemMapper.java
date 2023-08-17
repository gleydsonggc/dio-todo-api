package com.example.demo.mapper;

import com.example.demo.controller.dto.TodoItemDTO;
import com.example.demo.domain.TodoItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoItemMapper {

	TodoItemDTO toDto(TodoItem todoItem);

	TodoItem toModel(TodoItemDTO todoItemDTO);
}
