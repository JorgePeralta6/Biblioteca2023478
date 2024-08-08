package com.jorgeperalta.webapp.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Clientes")
public class Cliente {
    @Id
    private Long dpi;
    @NotNull(message = "NombreCliente no puede ser null")
    private String nombreCliente;
    @NotNull(message = "ApellidoCliente no puede ser null")
    private String apellidoCliente;
    @NotNull(message = "Telefono no puede ser null")
    private String telefono;
}
