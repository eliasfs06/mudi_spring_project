package br.com.alura.mvc.mudi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.mvc.mudi.dto.NovoPedidoDto;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.model.User;
import br.com.alura.mvc.mudi.repository.PedidoRepository;
import br.com.alura.mvc.mudi.repository.UserRepositoy;

@Controller
@RequestMapping("pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UserRepositoy userRepositoy;
	
	@GetMapping("formulario")
	public String formulario(NovoPedidoDto novoPedidoDto) {
		return "pedido/formulario";
	}
	
	@PostMapping("salvar")
	public String salvar(@Valid NovoPedidoDto novoPedido, BindingResult br) {
		
		if(br.hasErrors()) {
			return "pedido/formulario";
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepositoy.findUserByName(username);
		
		try {
			novoPedido.setStatus(StatusPedido.AGUARDANDO);
			novoPedido.setUser(user);
			pedidoRepository.save(novoPedido.toPedido());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "user/pedidos";
	}
	
}
