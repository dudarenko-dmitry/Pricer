//package pl.senla.pricer.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import pl.senla.pricer.dto.UserDto;
//
//
//// DELETE //
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return ResponseEntity.ok("User authenticated successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//        }
//    }
//}
