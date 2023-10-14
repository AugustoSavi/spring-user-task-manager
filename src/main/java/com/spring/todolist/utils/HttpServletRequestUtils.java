package com.spring.todolist.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.UUID;
public class HttpServletRequestUtils extends HttpServletRequestWrapper {

    public HttpServletRequestUtils(HttpServletRequest request) {
        super(request);
    }

    public UUID getIdUsuario(){
        return (UUID) getAttribute("idUsuario");
    }
}
