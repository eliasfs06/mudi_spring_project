package br.com.alura.mvc.mudi.api;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaRestController {
	
	@Autowired
	private PedidoRepository pedidoRepository; 
	
	@PostMapping
	public Oferta novaOferta(@Valid @RequestBody RequisicaoNovaOferta requisicao) {
		
		Optional<Pedido> pedidoBuscado = pedidoRepository.findById(requisicao.getPedidoId());
		if(!pedidoBuscado.isPresent())
			return null;	
		Pedido pedido = pedidoBuscado.get();
		
		Oferta novaOferta = requisicao.toOferta();
		
		novaOferta.setPedido(pedido);
		pedido.getOferta().add(novaOferta);
		
		pedidoRepository.save(pedido);
		
		return novaOferta;
	}
}