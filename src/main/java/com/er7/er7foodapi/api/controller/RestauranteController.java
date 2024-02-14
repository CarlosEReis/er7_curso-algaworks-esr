package com.er7.er7foodapi.api.controller;

import com.er7.er7foodapi.api.assembler.RestauranteInputDisassenbler;
import com.er7.er7foodapi.api.assembler.RestauranteModelAssembler;
import com.er7.er7foodapi.api.model.RestauranteModel;
import com.er7.er7foodapi.api.model.input.RestauranteInput;
import com.er7.er7foodapi.core.validation.ValidacaoException;
import com.er7.er7foodapi.domain.exception.CidadeNaoEncontradaException;
import com.er7.er7foodapi.domain.exception.CozinhaNaoEncontradaException;
import com.er7.er7foodapi.domain.exception.NegocioException;
import com.er7.er7foodapi.domain.model.Restaurante;
import com.er7.er7foodapi.domain.repository.RestauranteRepository;
import com.er7.er7foodapi.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired private SmartValidator validator;
    @Autowired private RestauranteRepository restauranteRepository;
    @Autowired private CadastroRestauranteService restauranteService;
    @Autowired private RestauranteModelAssembler restauranteModelAssembler;
    @Autowired private RestauranteInputDisassenbler restauranteInputDisassenbler;

    @GetMapping
    public List<RestauranteModel> listar() {
        return restauranteModelAssembler.toCollectionModel(this.restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = this.restauranteService.buscarOuFalhar(restauranteId);
        return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
        public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassenbler.toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(this.restauranteService.adicionar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            //Restaurante restaurante = restauranteInputDisassenbler.toDomainObject(restauranteInput);
            var restauranteDB = this.restauranteService.buscarOuFalhar(restauranteId);
            restauranteInputDisassenbler.copyToDomainObject(restauranteInput, restauranteDB);
            return restauranteModelAssembler.toModel(this.restauranteService.adicionar(restauranteDB));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteID}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteID) {
        restauranteService.ativar(restauranteID);
    }

    @DeleteMapping("/{restauranteID}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteID) {
        restauranteService.inativar(restauranteID);
    }

    @PutMapping("/{restauranteID}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteID) {
        restauranteService.abrir(restauranteID);
    }

    @PutMapping("/{restauranteID}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteID) {
        restauranteService.fechar(restauranteID);
    }

    /*@PatchMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        var restauranteDB = this.restauranteService.buscarOuFalhar(restauranteId);
        merge(campos, restauranteDB, request);
        validate(restauranteDB, "restaurante");
        return this.atualizar(restauranteId, restauranteDB);
    }*/

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        this.validator.validate(restaurante, bindingResult);
        if (bindingResult.hasErrors()) throw new ValidacaoException(bindingResult);
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
            campos.forEach((campo, valor) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, campo);
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            } );
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

}
