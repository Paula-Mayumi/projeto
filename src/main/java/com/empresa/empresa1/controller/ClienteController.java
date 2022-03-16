package com.empresa.empresa1.controller;







import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




import com.empresa.empresa1.model.Cliente;
import com.empresa.empresa1.repository.ClienteRepository;





@Controller
public class ClienteController {
	
	@Autowired
	private ClienteRepository cr;
	
	// chamo o form de cadastrar clientes
		@RequestMapping(value = "/cadastrarCliente")
		public String form() {
			return "cliente/form-cliente";
		}

		// cadastra clientes
		@RequestMapping(value = "/cadastrarCliente", method = RequestMethod.POST)
		public String form(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {

			if (result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os campos");
				return "redirect:/cadastrarCliente";
			}

			cr.save(cliente);
			attributes.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso!");
			return "redirect:/cadastrarCliente";
		}

		// listar cliente
		@RequestMapping("/clientes")
		public ModelAndView listaClientes() {
			ModelAndView mv = new ModelAndView("cliente/lista-cliente");
			Iterable<Cliente> clientes = cr.findAll();
			mv.addObject("clientes", clientes);
			return mv;
		}
		
		
		// GET que lista detalhes dos cliente
		@RequestMapping("/detalhes-cliente/{id}")
		public ModelAndView detalhesCliente(@PathVariable("id") long id) {
			Cliente cliente = cr.findById(id);
			ModelAndView mv = new ModelAndView("cliente/detalhes-cliente");
			mv.addObject("clientes", cliente);

			

			return mv;

		}

		
		//deleta cliente
		@RequestMapping("/deletarCliente")
		public String deletarCliente(long id) {
			Cliente cliente = cr.findById(id);
			cr.delete(cliente);
			return "redirect:/clientes";
			
		}
		
		// Métodos que atualizam cliente
		//form
		@RequestMapping(value="/editar-cliente", method = RequestMethod.GET)
		public ModelAndView editarCliente(long id) {
			Cliente cliente = cr.findById(id);
			ModelAndView mv = new ModelAndView("cliente/update-cliente");
			mv.addObject("cliente", cliente);
			return mv;
		}
		
		
		
		// update cliente
		@RequestMapping(value = "/editar-cliente", method = RequestMethod.POST)
		public String updateCliente(@Valid Cliente cliente,  BindingResult result, RedirectAttributes attributes){
			
			cr.save(cliente);
			attributes.addFlashAttribute("successs", "Cliente alterado com sucesso!");
			
			long idLong = cliente.getId();
			String id = "" + idLong;
			return "redirect:/detalhes-cliente/" + id;
			
	
			
		}
		

		
			
		
		
		
		
		
		

	}


