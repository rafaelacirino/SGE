package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.InscricaoResposta;
import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.EventoRepositorio;
import com.basis.sge.service.repositorio.InscricaoRespostaRepositorio;
import com.basis.sge.service.repositorio.PerguntaRepositorio;
import com.basis.sge.service.repositorio.PreInscricaoRepositorio;
import com.basis.sge.service.repositorio.SituacaoPreInscricaoRepositorio;
import com.basis.sge.service.repositorio.UsuarioRepositorio;
import com.basis.sge.service.servico.dto.CancelarInscricaoDTO;
import com.basis.sge.service.servico.dto.EmailDTO;
import com.basis.sge.service.servico.dto.ListagemInscricoesDTO;
import com.basis.sge.service.servico.dto.PreInscricaoDTO;
import com.basis.sge.service.servico.dto.PreinscricaoUsuarioDTO;
import com.basis.sge.service.servico.mapper.IncricaoRespostaMapper;
import com.basis.sge.service.servico.mapper.PerguntaMapper;
import com.basis.sge.service.servico.mapper.PreInscricaoMapper;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.producer.SgeProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PreInscricaoServico {

    private final PreInscricaoRepositorio preInscricaoRepositorio;
    private final EventoRepositorio eventoRepositorio;
    private final PreInscricaoMapper preInscricaoMapper;
    private final PerguntaMapper perguntaMapper;
    private final IncricaoRespostaMapper incricaoRespostaMapper;
    private final InscricaoRespostaRepositorio inscricaoRespostaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PerguntaRepositorio perguntaRepositorio;
    private final SituacaoPreInscricaoRepositorio situacaoPreInscricaoRepositorio;
    private final SgeProducer sgeProducer;
    private static final Integer ID_SITUACAO_INSCRICAO_CANCELADA = 4;
    private static final Integer ID_SITUACAO_INSCRICAO_ACEITA = 2;
    private static final Integer ID_SITUACAO_INSCRICAO_AGUARDANDO_APROVACAO = 1;

    public List<PreInscricaoDTO> listar(){
        List<PreInscricao> preInscricaos = preInscricaoRepositorio.findAll();
        return preInscricaoMapper.toDto(preInscricaos);
    }

    public PreInscricaoDTO buscarPorId(Integer id){
        PreInscricao preInscricao = preInscricaoRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Pré Inscrição não encontrado"));
        return preInscricaoMapper.toDto(preInscricao);
    }

    public PreInscricaoDTO salvar(PreInscricaoDTO preInscricaoDTO){
        PreInscricao preInscricao = preInscricaoMapper.toEntity(preInscricaoDTO);

        List<PreInscricao> preInscricaosDoUsuario = preInscricaoRepositorio.findByUsuario(preInscricao.getUsuario());
        for (PreInscricao preInscricaoUsuario: preInscricaosDoUsuario) {
            if(preInscricaoUsuario.getEvento().getId() == preInscricaoDTO.getIdEvento()
                && (preInscricaoUsuario.getSituacaoPreInscricao().getId() == ID_SITUACAO_INSCRICAO_ACEITA
                    || preInscricaoUsuario.getSituacaoPreInscricao().getId() == ID_SITUACAO_INSCRICAO_AGUARDANDO_APROVACAO)){
                throw new RegraNegocioException("Inscrição inválida, usuário já inscrito neste evento.");
            }
        }

        List<InscricaoResposta> inscricaoRespostas = preInscricao.getInscricaoRespostas();
        preInscricao.setInscricaoRespostas(new ArrayList<>());
        preInscricaoRepositorio.save(preInscricao);
        inscricaoRespostas.forEach(inscricaoResposta -> inscricaoResposta.setPreInscricao(preInscricao));
        inscricaoRespostaRepositorio.saveAll(inscricaoRespostas);
        return preInscricaoMapper.toDto(preInscricao);
    }

    public PreInscricaoDTO atualizar(PreInscricaoDTO preInscricaoDTO){
        PreInscricao preInscricao = preInscricaoRepositorio.findById(preInscricaoDTO.getId())
                .orElseThrow(() -> new RegraNegocioException("A pre inscrição não existe"));

        if(!preInscricao.getEvento().getId().equals(preInscricaoDTO.getIdEvento())
                || !preInscricao.getUsuario().getId().equals(preInscricaoDTO.getIdUsuario())){
            throw new RegraNegocioException("Só poderá ser editada a situação na Inscrição");
        }

       if(!preInscricaoDTO.getIdSituacaoPreInscricao().equals(preInscricao.getSituacaoPreInscricao().getId())){
            
       }

        preInscricaoRepositorio.save(preInscricaoMapper.toEntity(preInscricaoDTO));

        return preInscricaoMapper.toDto(preInscricao);
    }

    public void remover(Integer id){
        PreInscricao preInscricao = preInscricaoRepositorio.findById(id).orElseThrow(()-> new RegraNegocioException("Inscricao invalida"));

        criarEmailInscricaoCancelada(preInscricaoMapper.toDto(preInscricao));
        preInscricaoRepositorio.deleteById(id);
    }

    public List<PreInscricaoDTO>        buscarPreinscricaoPorIdEvento(Integer id){
        List<PreInscricaoDTO> preInscricoesPorIdEvento = new ArrayList<>();
        List<PreInscricaoDTO> preInscricoes = preInscricaoMapper.toDto(preInscricaoRepositorio.findAll());
        for (PreInscricaoDTO preInscricao: preInscricoes) {
            if(preInscricao.getIdEvento().equals(id)){
                preInscricoesPorIdEvento.add(preInscricao);
            }
        }
        return preInscricoesPorIdEvento;
    }


    public void editarInscricaoCancelada(CancelarInscricaoDTO cancelarInscricaoDTO) {
        Usuario usuario = usuarioRepositorio.findByChaveUnica(cancelarInscricaoDTO.getChave());

        PreInscricao preInscricao = preInscricaoRepositorio.findById(cancelarInscricaoDTO.getId())
                .orElseThrow(() -> new RegraNegocioException("Não existe inscrição com esse id"));

        if(!preInscricao.getUsuario().getId().equals(usuario.getId())){
            throw new RegraNegocioException("Essa Inscricao não é desse usuario");
        }

        preInscricao.setSituacaoPreInscricao(situacaoPreInscricaoRepositorio
                .findById(ID_SITUACAO_INSCRICAO_CANCELADA).orElse(new SituacaoPreInscricao()));
        preInscricaoRepositorio.save(preInscricao);
        criarEmailInscricaoCancelada(preInscricaoMapper.toDto(preInscricao));
    }
    public void criarEmailInscricaoEditada(PreInscricaoDTO preInscricaoDTO){
        PreInscricao preInscricao = preInscricaoMapper.toEntity(preInscricaoDTO);

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Alteração de inscrição no evento " + preInscricao.getEvento().getTitulo());
        emailDTO.setCorpo("Sua inscrição foi avaliada, sua situação é: " + preInscricao.getSituacaoPreInscricao());
        emailDTO.setDestinatario(preInscricao.getUsuario().getEmail());
        emailDTO.setCopias(new ArrayList< >());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        this.sgeProducer.sendMail(emailDTO);
    }

    public void criarEmailInscricaoCancelada(PreInscricaoDTO preInscricaoDTO){
        PreInscricao preInscricao = preInscricaoMapper.toEntity(preInscricaoDTO);

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Cancelamento de inscrição no evento " + preInscricao.getEvento().getTitulo());
        emailDTO.setCorpo("Sua inscrição foi cancelada");
        emailDTO.setDestinatario(preInscricao.getUsuario().getEmail());
        emailDTO.setCopias(new ArrayList< >());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        this.sgeProducer.sendMail(emailDTO);
    }

    public List<ListagemInscricoesDTO> buscarPreIncricoesPoEvento(Integer id){

        Evento evento = eventoRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Evento com id não encontrado"));

        List<PreInscricao> preInscricaos = preInscricaoRepositorio.findByEvento(evento);
        List<Pergunta> perguntasDaInscricao = new ArrayList<>();
        List<ListagemInscricoesDTO> listagemInscricoesDTOS = new ArrayList<>();

        for (PreInscricao preInscricao: preInscricaos) {
            ListagemInscricoesDTO listagemInscricoesDTO = new ListagemInscricoesDTO();
            listagemInscricoesDTO.setId(preInscricao.getId());
            listagemInscricoesDTO.setNomeUsuario(preInscricao.getUsuario().getNome());
            listagemInscricoesDTO.setEmailUsuario(preInscricao.getUsuario().getEmail());
            listagemInscricoesDTO.setInscricoesResposta(incricaoRespostaMapper.toDto(preInscricao.getInscricaoRespostas()));
            listagemInscricoesDTO.setIdSituacao(preInscricao.getSituacaoPreInscricao().getId());
            listagemInscricoesDTO.setSituacaoDescricao(preInscricao.getSituacaoPreInscricao().getDescricao());

            for (InscricaoResposta inscricaoResposta: preInscricao.getInscricaoRespostas()) {
                Pergunta pergunta = perguntaRepositorio.findById(inscricaoResposta.getPergunta().getId())
                        .orElseThrow(() -> new RegraNegocioException("Pergunta não encontrada"));
                perguntasDaInscricao.add(pergunta);
            }
            listagemInscricoesDTO.setPerguntas(perguntaMapper.toDto(perguntasDaInscricao));

            listagemInscricoesDTOS.add(listagemInscricoesDTO);
            perguntasDaInscricao = new ArrayList<>();
        }

        return listagemInscricoesDTOS;
    }

    public List<PreinscricaoUsuarioDTO> buscarPreinscricaoPorIdUsuario(Integer id){
        List<PreinscricaoUsuarioDTO> preInscricoesPorIdUsuario = new ArrayList<>();
        Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("Usuario não encontrado"));
        List<PreInscricao> preInscricoesDoUsuario = preInscricaoRepositorio.findByUsuario(usuario);


        for (PreInscricao inscricoes: preInscricoesDoUsuario) {
            PreinscricaoUsuarioDTO inscricao = new PreinscricaoUsuarioDTO(
                    inscricoes.getId(),
                    inscricoes.getEvento().getTitulo(),
                    inscricoes.getEvento().getPeriodoInicio(),
                    inscricoes.getEvento().getPeriodoFim(),
                    inscricoes.getEvento().getDescricao(),
                    inscricoes.getSituacaoPreInscricao().getDescricao(),
                    inscricoes.getSituacaoPreInscricao().getId());
            preInscricoesPorIdUsuario.add(inscricao);
        }
        return preInscricoesPorIdUsuario;
    }
}
