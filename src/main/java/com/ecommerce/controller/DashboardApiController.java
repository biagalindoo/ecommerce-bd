package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardApiController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/indicadores")
    public ResponseEntity<DashboardIndicatorsDTO> indicadores(
            @RequestParam(value = "inicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(value = "fim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(dashboardService.buscarIndicadores(inicio, fim));
    }

    @GetMapping("/pedidos-status")
    public ResponseEntity<List<StatusCountDTO>> pedidosPorStatus(
            @RequestParam(value = "inicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(value = "fim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(dashboardService.pedidosPorStatus(inicio, fim));
    }

    @GetMapping("/vendas-mensais")
    public ResponseEntity<List<MonthlySalesDTO>> vendasMensais(
            @RequestParam(value = "inicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(value = "fim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(dashboardService.vendasMensais(inicio, fim));
    }

    @GetMapping("/top-produtos")
    public ResponseEntity<List<ProductSalesDTO>> topProdutos(
            @RequestParam(value = "inicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(value = "fim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(value = "limite", defaultValue = "5") int limite) {
        return ResponseEntity.ok(dashboardService.produtosMaisVendidos(inicio, fim, limite));
    }

    @GetMapping("/estoque-distribuicao")
    public ResponseEntity<StockDistributionDTO> estoqueDistribuicao() {
        return ResponseEntity.ok(dashboardService.distribuicaoEstoque());
    }

    @GetMapping("/fornecedores")
    public ResponseEntity<List<SupplierContributionDTO>> fornecedores(
            @RequestParam(value = "limite", defaultValue = "5") int limite) {
        return ResponseEntity.ok(dashboardService.fornecedores(limite));
    }

    @GetMapping("/estatisticas-precos")
    public ResponseEntity<PriceStatisticsDTO> estatisticasPrecos() {
        return ResponseEntity.ok(dashboardService.estatisticasPrecos());
    }

    @GetMapping("/visao-completa")
    public ResponseEntity<Map<String, Object>> visaoCompleta(
            @RequestParam(value = "inicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(value = "fim", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(value = "limiteProdutos", defaultValue = "5") int limiteProdutos,
            @RequestParam(value = "limiteFornecedores", defaultValue = "5") int limiteFornecedores) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("indicadores", dashboardService.buscarIndicadores(inicio, fim));
        payload.put("pedidosStatus", dashboardService.pedidosPorStatus(inicio, fim));
        payload.put("vendasMensais", dashboardService.vendasMensais(inicio, fim));
        payload.put("topProdutos", dashboardService.produtosMaisVendidos(inicio, fim, limiteProdutos));
        payload.put("estoque", dashboardService.distribuicaoEstoque());
        payload.put("fornecedores", dashboardService.fornecedores(limiteFornecedores));
        payload.put("estatisticasPrecos", dashboardService.estatisticasPrecos());
        return ResponseEntity.ok(payload);
    }
}


