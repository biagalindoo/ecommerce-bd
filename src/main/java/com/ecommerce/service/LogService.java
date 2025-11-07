package com.ecommerce.service;

import com.ecommerce.dao.LogDAO;
import com.ecommerce.entity.LogAuditoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    @Autowired
    private LogDAO logDAO;

    public List<LogAuditoria> listarRecentes(int limite) {
        return logDAO.listarRecentes(limite);
    }
}


