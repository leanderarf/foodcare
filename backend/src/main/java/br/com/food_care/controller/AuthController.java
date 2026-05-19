    package br.com.food_care.controller;

    import br.com.food_care.dto.LoginResponseDTO;
    import br.com.food_care.service.AuthService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.responses.ApiResponses;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.Map;

    @CrossOrigin(origins = "http://localhost:3000")
    @RestController
    @RequestMapping("/auth")
    @Tag(name = "Autenticação", description = "Endpoint responsável por validar acessos e realizar o Login na plataforma")
    public class AuthController {

        @Autowired
        private AuthService authenticatorService;

        @Operation(summary = "Autenticar usuário", description = "Valida o e-mail e senha informados. Retorna os dados necessários para o LocalStorage do Frontend React (incluindo o Token e o tipoUsuario).")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso"),
                @ApiResponse(responseCode = "401", description = "Credenciais inválidas (E-mail ou Senha incorretos)")
        })
        @PostMapping("/login")
        public ResponseEntity<LoginResponseDTO> login(@RequestBody Map<String, String> dados) {

            String email = dados.get("email");
            String senha = dados.get("senha");

            LoginResponseDTO response = authenticatorService.realizarLogin(email, senha);

            return ResponseEntity.ok(response);
        }
    }