package br.edu.matriculas.persistencia;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class ArquivoUtil {

    private static final String SEPARADOR = "|";

    private ArquivoUtil() {
    }

    static List<String[]> lerLinhas(Path arquivo) {
        List<String[]> linhas = new ArrayList<>();
        if (!Files.exists(arquivo)) {
            return linhas;
        }
        try {
            for (String linha : Files.readAllLines(arquivo, StandardCharsets.UTF_8)) {
                if (!linha.isBlank()) {
                    linhas.add(linha.split("\\" + SEPARADOR, -1));
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao ler " + arquivo, e);
        }
        return linhas;
    }

    static void escreverLinhas(Path arquivo, List<String[]> campos) {
        try {
            Files.createDirectories(arquivo.getParent());
            List<String> linhas = new ArrayList<>();
            for (String[] campo : campos) {
                linhas.add(String.join(SEPARADOR, campo));
            }
            Files.write(arquivo, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao escrever " + arquivo, e);
        }
    }
}
