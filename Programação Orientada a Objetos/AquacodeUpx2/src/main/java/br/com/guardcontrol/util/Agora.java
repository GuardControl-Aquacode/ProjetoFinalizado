package br.com.guardcontrol.util;
import java.time.LocalDateTime;
import java.time.ZoneId;
public final class Agora {
    private Agora() { }
    public static LocalDateTime brasil() { return LocalDateTime.now(ZoneId.of("America/Sao_Paulo")); }
}
