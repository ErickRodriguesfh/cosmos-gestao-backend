package br.ebr.cosmos.cosmos_gestao.application.dto;

import java.util.List;

public record LoginResponseDTO(String jwtToken, String name, Long userId, List<String> roles) {



}
