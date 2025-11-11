package com.ecommerce.dto;

/**
 * Contagem por status (pedidos, etc).
 */
public class StatusCountDTO {

    private String status;
    private long total;

    public StatusCountDTO() {
    }

    public StatusCountDTO(String status, long total) {
        this.status = status;
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}


