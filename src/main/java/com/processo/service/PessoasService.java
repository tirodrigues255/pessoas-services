package com.processo.service;

import com.processo.model.Pessoa;
import com.processo.model.dto.PessoaDTO;
import com.processo.repository.PessoasRepository;
import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class PessoasService {

    @Autowired
    private PessoasRepository repository;

    public Page<PessoaDTO> getAll(Pageable pageable) throws Exception {
        Page<Pessoa> page = repository.findAll(pageable);
        if (page.isEmpty()) {
            throw new Exception("Não encontrado nenhum registro no bando de dados.");
        }
        return page.map((t) -> {
            PessoaDTO dto = new PessoaDTO();
            BeanUtils.copyProperties(t, dto);
            return dto;
        });
    }

    public PessoaDTO getById(String id) throws Exception {
        Optional<Pessoa> pessoa = repository.findById(id);
        if (!pessoa.isPresent()) {
            throw new Exception("Não encontrado registro usando id '" + id + "'.");
        }
        PessoaDTO dto = new PessoaDTO();
        BeanUtils.copyProperties(pessoa.get(), dto);
        return dto;
    }

    public Pessoa create(@Valid Pessoa pessoa) throws Exception {
        if (repository.findByCpf(pessoa.getCpf()) != null) {
            throw new Exception("Cadastro com o CPF '" + pessoa.getCpf() + "' já existente.");
        }
        return repository.insert(pessoa);
    }

    public void saveOrUpdate(@Valid Pessoa pessoa) throws Exception {
        pessoa.setCreatedAt(null);
        pessoa.setModifiedAt(null);
        Optional<Pessoa> optionalPessoa = repository.findById(pessoa.getId());
        if (!optionalPessoa.isPresent()) {
            throw new Exception("Não encontrado registro usando o  id '" + pessoa.getId() + "' para ser atualizado.");
        }
        if (repository.findByIdNotAndCpf(pessoa.getId(), pessoa.getCpf()) != null) {
            throw new Exception("Não pode atualizar com esse CPF '" + pessoa.getCpf() + "', pois já existe outro registro usando ele.");
        }
        Pessoa pessoaUpdated = optionalPessoa.get();
        BeanUtils.copyProperties(pessoa, pessoaUpdated, getNullPropertyNames(pessoa));
        pessoaUpdated.setModifiedAt(new Date());
        repository.save(pessoaUpdated);
    }

    public void delete(String id) throws Exception {
        Optional<Pessoa> pessoa = repository.findById(id);
        if (!pessoa.isPresent()) {
            throw new Exception("Não encontrado registro usando id '" + id + "' para ser deletado.");
        }
        repository.deleteById(id);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> nullPropNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                nullPropNames.add(pd.getName());
            }
        }
        String[] result = new String[nullPropNames.size()];
        return nullPropNames.toArray(result);
    }
}
