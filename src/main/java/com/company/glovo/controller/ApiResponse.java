package com.company.glovo.controller;

import lombok.Data;


@Data
public class ApiResponse<D> {
    private boolean success;
    private D data;
}