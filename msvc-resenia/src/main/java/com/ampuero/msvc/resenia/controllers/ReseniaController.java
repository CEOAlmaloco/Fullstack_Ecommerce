package com.ampuero.msvc.resenia.controllers;

import com.ampuero.msvc.resenia.dtos.ReseniaCreationDTO;
import com.ampuero.msvc.resenia.dtos.ReseniaResponseDTO;
import com.ampuero.msvc.resenia.dtos.ReseniaUpdateDTO;
import com.ampuero.msvc.resenia.models.Resenia;
import com.ampuero.msvc.resenia.services.ReseniaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resenias")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:3000"})
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    @PostMapping("/producto/{idProducto}")
    public ResponseEntity<ReseniaResponseDTO> crearResenia(
            @PathVariable Long idProducto,
            @Valid @RequestBody ReseniaCreationDTO reseniaDTO) {
        reseniaDTO.setIdProducto(idProducto);
        Resenia resenia = reseniaService.crearResenia(reseniaDTO);
        ReseniaResponseDTO response = convertirReseniaAResponseDTO(resenia);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReseniaResponseDTO> traerReseniaPorId(@PathVariable Long id) {
        Resenia resenia = reseniaService.traerReseniaPorId(id);
        ReseniaResponseDTO response = convertirReseniaAResponseDTO(resenia);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<ReseniaResponseDTO>> traerReseniasPorProducto(@PathVariable Long idProducto) {
        List<Resenia> resenias = reseniaService.traerReseniasPorProducto(idProducto);
        List<ReseniaResponseDTO> response = resenias.stream()
                .map(this::convertirReseniaAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReseniaResponseDTO>> traerReseniasPorUsuario(@PathVariable Long idUsuario) {
        List<Resenia> resenias = reseniaService.traerReseniasPorUsuario(idUsuario);
        List<ReseniaResponseDTO> response = resenias.stream()
                .map(this::convertirReseniaAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReseniaResponseDTO>> traerTodasResenias() {
        List<Resenia> resenias = reseniaService.traerTodasResenias();
        List<ReseniaResponseDTO> response = resenias.stream()
                .map(this::convertirReseniaAResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/producto/{idProducto}/rating-promedio")
    public ResponseEntity<Map<String, Double>> calcularRatingPromedio(@PathVariable Long idProducto) {
        Double promedio = reseniaService.calcularRatingPromedio(idProducto);
        return ResponseEntity.ok(Map.of("ratingPromedio", promedio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReseniaResponseDTO> actualizarResenia(
            @PathVariable Long id,
            @Valid @RequestBody ReseniaUpdateDTO reseniaDTO) {
        Resenia resenia = reseniaService.actualizarResenia(id, reseniaDTO);
        ReseniaResponseDTO response = convertirReseniaAResponseDTO(resenia);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResenia(@PathVariable Long id) {
        reseniaService.eliminarResenia(id);
        return ResponseEntity.noContent().build();
    }

    private ReseniaResponseDTO convertirReseniaAResponseDTO(Resenia resenia) {
        ReseniaResponseDTO response = new ReseniaResponseDTO();
        response.setId(resenia.getId());
        response.setIdProducto(resenia.getIdProducto());
        response.setIdUsuario(resenia.getIdUsuario());
        response.setUsuarioNombre(resenia.getUsuarioNombre());
        response.setRating(resenia.getRating());
        response.setComentario(resenia.getComentario());
        response.setFechaCreacion(resenia.getFechaCreacion());
        response.setFechaActualizacion(resenia.getFechaActualizacion());
        response.setActivo(resenia.getActivo());
        return response;
    }
}

