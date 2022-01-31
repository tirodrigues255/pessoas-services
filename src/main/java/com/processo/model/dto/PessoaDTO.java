package com.processo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
public class PessoaDTO {

    private String id;

    private String nome;
    
    private String cpf;

    private String sexo;

    private String email;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataNascimento;

    private String naturalidade;

    private String nacionalidade;

    private Date createdAt;

    private Date modifiedAt;
}
