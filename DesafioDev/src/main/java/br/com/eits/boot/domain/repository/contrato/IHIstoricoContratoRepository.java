package br.com.eits.boot.domain.repository.contrato;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.contrato.HistoricoContrato;

public interface IHIstoricoContratoRepository extends JpaRepository<HistoricoContrato, Long>{

}
