package com.processo.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Document(collection = "Pessoas")
public class Pessoa implements Serializable {

    @Id
    private String id;

    @NotNull
    private String nome;

    @NotEmpty
    @Indexed(unique = true, sparse = true)
    @CPF(message = "CPF inválido")
    private String cpf;

    private String sexo;

    @Email(message = "E-mail mal formado")
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Data de nascimento é de preenchomento obrigatório")
    @Past
    private Date dataNascimento;

    private String naturalidade;

    private String nacionalidade;

    @CreatedDate
    private Date createdAt;

    @UpdateTimestamp
    private Date modifiedAt;
}
