package com.lukaszplawiak.auth;

class AuthenticationResponseDto {
    private final String token;

    public AuthenticationResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    } // tu musi byc publiczny! bo domyslna konfiguracja object mappera (object mapper zamienia objekt javovy na jsona) kozysta z getterow i tylko publicznych, wiec(gdy dostep pakietowy bylby to) w odpowiedzi na uzwierzytelnienie dostawalbym 200 ok i nic w body
}
