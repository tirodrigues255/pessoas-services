package com.processo.controller;

import com.processo.model.Pessoa;
import com.processo.model.dto.PessoaDTO;
import com.processo.service.PessoasService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pessoas")
public class PessoasController {

    @Autowired
    private PessoasService pessoasService;

    @GetMapping
    public ResponseEntity<Page<PessoaDTO>> getAll(Pageable pageable) throws Exception {
        Page<PessoaDTO> pessoas = pessoasService.getAll(pageable);
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getById(@PathVariable("id") String id) throws Exception {
        PessoaDTO dto = pessoasService.getById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> create(@RequestBody PessoaDTO dto) throws Exception {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(dto, pessoa);
        BeanUtils.copyProperties(pessoasService.create(pessoa), dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PessoaDTO dto) throws Exception {
        Pessoa pessoa = new Pessoa();        
        BeanUtils.copyProperties(dto, pessoa);        
        pessoasService.saveOrUpdate(pessoa);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) throws Exception {
        pessoasService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
