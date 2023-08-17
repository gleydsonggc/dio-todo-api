package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoItemDTO {

	private Long id;

	@NotBlank
	private String title;

	private String description;

	private boolean done;

	private LocalDateTime created;

	private LocalDateTime completed;

}
