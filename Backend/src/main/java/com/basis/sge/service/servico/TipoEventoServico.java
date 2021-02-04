package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.TipoEvento;
import com.basis.sge.service.repositorio.TipoEventoRepositorio;
import com.basis.sge.service.servico.dto.TipoEventoDTO;
import com.basis.sge.service.servico.mapper.TipoEventoMapper;
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

    public TipoEventoDTO salvar(TipoEventoDTO tipoEventoDTO){
        TipoEvento tipoEvento = tipoEventoRepositorio.save(tipoEventoMapper.toEntity(tipoEventoDTO));
        return tipoEventoMapper.toDto(tipoEvento);
    }

    public TipoEventoDTO buscarPorId(Integer id){
        return tipoEventoMapper.toDto(tipoEventoRepositorio.findById(id).get());
    }
}
