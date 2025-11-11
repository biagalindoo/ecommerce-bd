package com.ecommerce.service;

import com.ecommerce.dao.DashboardDAO;
import com.ecommerce.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private DashboardDAO dashboardDAO;

    public DashboardIndicatorsDTO buscarIndicadores(LocalDate inicio, LocalDate fim) {
        return dashboardDAO.buscarIndicadores(inicio, fim);
    }

    public List<StatusCountDTO> pedidosPorStatus(LocalDate inicio, LocalDate fim) {
        return dashboardDAO.pedidosPorStatus(inicio, fim);
    }

    public List<MonthlySalesDTO> vendasMensais(LocalDate inicio, LocalDate fim) {
        return dashboardDAO.vendasMensais(inicio, fim);
    }

    public List<ProductSalesDTO> produtosMaisVendidos(LocalDate inicio, LocalDate fim, int limite) {
        return dashboardDAO.produtosMaisVendidos(inicio, fim, limite);
    }

    public StockDistributionDTO distribuicaoEstoque() {
        return dashboardDAO.distribuicaoEstoque();
    }

    public List<SupplierContributionDTO> fornecedores(int limite) {
        return dashboardDAO.fornecedoresContribuicao(limite);
    }

    public PriceStatisticsDTO estatisticasPrecos() {
        return dashboardDAO.estatisticasPrecos();
    }
}


