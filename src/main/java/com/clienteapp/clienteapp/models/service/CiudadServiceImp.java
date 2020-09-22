package com.clienteapp.clienteapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clienteapp.clienteapp.models.entity.Ciudad;
import com.clienteapp.clienteapp.models.repository.CiudadRepository;

@Service
public class CiudadServiceImp implements ICiudadService {
	
	@Autowired
	private CiudadRepository ciudadRepository;

	@Override
	public List<Ciudad> listaCiudades() {
		
		return (List<Ciudad>) ciudadRepository.findAll();
	}

}
