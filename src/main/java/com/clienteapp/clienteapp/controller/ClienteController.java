package com.clienteapp.clienteapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clienteapp.clienteapp.models.entity.Ciudad;
import com.clienteapp.clienteapp.models.entity.Cliente;
import com.clienteapp.clienteapp.models.service.ICiudadService;
import com.clienteapp.clienteapp.models.service.IClienteService;

@Controller
@RequestMapping("/views/clientes")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private ICiudadService ciudadService;
	
	@GetMapping("/")
	public String listarClientes(Model model) {
		
		List<Cliente> listadoClientes=clienteService.listarTodos();
		model.addAttribute("clientes", listadoClientes);
		model.addAttribute("titulo", "Lista de Clientes");
		return "/views/clientes/listar";
	}
	
	@GetMapping("/create")
	public String crear(Model model) {
		
		Cliente cliente=new Cliente();
		List<Ciudad> listCiudades=ciudadService.listaCiudades();
		
		
		model.addAttribute("titulo", "Formulario: Nuevo Cliente");
		model.addAttribute("cliente", cliente);
		model.addAttribute("ciudades", listCiudades);
		return "/views/clientes/fmrCrear";
	}
	
	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result,
			Model model, RedirectAttributes attribute) {
		List<Ciudad> listCiudades=ciudadService.listaCiudades();
		if(result.hasErrors()) {
			
			model.addAttribute("titulo", "Formulario: Nuevo Cliente");
			model.addAttribute("cliente", cliente);
			model.addAttribute("ciudades", listCiudades);
			System.out.println("hubo errores en el form");
			return "/views/clientes/fmrCrear";
		}
		
		clienteService.guardar(cliente);
		System.out.println("cliente guardado");
		attribute.addFlashAttribute("success", "cliente guardado");
		return "redirect:/views/clientes/";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idCliente, Model model, RedirectAttributes attribute) {
		
		Cliente cliente=null;
		if(idCliente>0) {
			cliente=clienteService.buscarPorId(idCliente);
			if(cliente==null) {
				System.out.println("Error el id el cliente no existe");
				attribute.addFlashAttribute("error", "Atencion: el id el cliente no existe");
				return "redirect:/views/clientes/";
			}
		}else {
			attribute.addFlashAttribute("error", "Atencion: error con el id del cliente");
			System.out.println("Error el id el cliente no existe");
			return "redirect:/views/clientes/";
		}
		
		
		
		List<Ciudad> listCiudades=ciudadService.listaCiudades();
		
		
		model.addAttribute("titulo", "Formulario: Editar Cliente");
		model.addAttribute("cliente", cliente);
		model.addAttribute("ciudades", listCiudades);
		return "/views/clientes/fmrCrear";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idCliente, RedirectAttributes attribute) {
		
		
		Cliente cliente=null;
		if(idCliente>0) {
			cliente=clienteService.buscarPorId(idCliente);
			if(cliente==null) {
				attribute.addFlashAttribute("error", "Atencion: el id el cliente no existe");
				System.out.println("Error el id el cliente no existe");
				return "redirect:/views/clientes/";
			}
		}else {
			attribute.addFlashAttribute("error", "Atencion: error con el id del cliente");
			System.out.println("Error el id el cliente no existe");
			return "redirect:/views/clientes/";
		}
		
		
		clienteService.eliminar(idCliente);
		attribute.addFlashAttribute("success", "Registro eliminado");
		System.out.println("Registro eliminado");
		return "redirect:/views/clientes/";
	}

}
