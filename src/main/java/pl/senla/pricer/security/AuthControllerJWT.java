//package pl.senla.pricer.security;
//
//@RestController
//public class AuthControllerJWT {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtConfig jwtConfig;
//
//    @Autowired
//    private JwtUserDetailsService jwtUserDetailsService;
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
//        try {
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginForm.getUsername());
//            String token = Jwts.builder()
//                    .setSubject(userDetails.getUsername())
//                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
//                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
//                    .compact();
//
//
//            return ResponseEntity.ok(token);
//        } catch (Exception e) {
//
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//        }
//    }
//
//}
