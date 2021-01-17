package com.basis.sge.service.servico;

import com.basis.sge.service.repositorio.TipoEventoRepositorio;
import com.basis.sge.service.servico.DTO.TipoEventoDTO;
import com.basis.sge.service.servico.mapper.TipoEventoMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TipoEventoServico {

    private final TipoEventoRepositorio tipoEventoRepositorio;
    private final TipoEventoMapper tipoEventoMapper;

    public List<TipoEventoDTO> listar(){
        return tipoEventoMapper.toDto(tipoEventoRepositorio.findAll());
    }

    public TipoEventoDTO buscarPorId(Integer id){
        return tipoEventoMapper.toDto(tipoEventoRepositorio.findById(id).get());
    }
}
