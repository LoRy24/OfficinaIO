package com.github.lory24.officinaio;

import com.github.lory24.officinaio.core.Fattura;
import com.github.lory24.officinaio.core.Officina;
import com.github.lory24.officinaio.core.Prenotazione;
import com.github.lory24.officinaio.core.Servizio;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Il provider delle fatture è il componente del programma che si dedica alla creazione e al salvataggio delle fatture,
 * alla loro creazione e al loro salvataggio nel registro del programma.
 */
public class ProviderFatture {
    // Contiene:
    // ANNO
    // NUMERO RAGGIUNTO
    private final File datiNumeri = new File("./data/storage.dat");
    private int currentNumber;

    @SneakyThrows
    public void load() {
        if (datiNumeri.createNewFile()) {
            // Completa il file con i dati di base
            inizializzaStorage();

            // Inizializza
            this.currentNumber = 1;
        }
        else {
            // Carica dal file
            FileInputStream fileInputStream = new FileInputStream(this.datiNumeri);
            String[] content = new String(fileInputStream.readAllBytes()).split("\n");
            fileInputStream.close();

            // Controlla se l'anno è cambiato e bisogna rigenerare il registro
            if (Integer.parseInt(content[0]) < Calendar.getInstance().get(Calendar.YEAR)) {
                this.inizializzaStorage();
                this.currentNumber = 1;
            }
            else {
                this.currentNumber = Integer.parseInt(content[1]);
            }
        }
    }

    public Fattura creaFattura(String nomeCliente, String cognomeCliente, String dataDiNascitaCliente, String luogoDiNascitaCliente, String residenzaCliente, String codiceFiscale, @NotNull Officina officina, @NotNull Prenotazione prenotazione) {
        // Crea le voci
        List<Fattura.Voce> voci = new ArrayList<>();

        // Per ciascun servizio
        for (Servizio s: prenotazione.getServizi()) {
            List<String> operazioni = new ArrayList<>();
            for (Servizio.Operazione op: s.getOperazioni()) {
                operazioni.add(op.descrizione());
            }

            // Crea la voce e aggiungila
            voci.add(new Fattura.Voce(s.getTipoServizio().getTipo(), operazioni, s.calcolaCosto()));
        }

        // Crea la fattura
        return this.creaFattura(
                nomeCliente,
                cognomeCliente,
                dataDiNascitaCliente,
                luogoDiNascitaCliente,
                residenzaCliente,
                codiceFiscale,
                officina,
                voci
        );
    }

    public Fattura creaFattura(String nomeCliente, String cognomeCliente, String dataDiNascitaCliente, String luogoDiNascitaCliente, String residenzaCliente, String codiceFiscale, @NotNull Officina officina, @NotNull List<Fattura.Voce> voci) {
        Fattura fattura = new Fattura(
                nomeCliente,
                cognomeCliente,
                dataDiNascitaCliente,
                luogoDiNascitaCliente,
                residenzaCliente,
                codiceFiscale,
                officina,
                this.currentNumber
        );
        this.incrementaNumeroProgressivo();

        // Aggiungi le voci
        for (Fattura.Voce v: voci) {
            fattura.addVoce(v);
        }

        return fattura;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    public void salvaFattura(@NotNull Fattura fattura) {
        new File("./fatture/" + Calendar.getInstance().get(Calendar.YEAR) + "/").mkdir();
        File fileFattura = new File("./fatture/" + Calendar.getInstance().get(Calendar.YEAR) + "/fattura_" + fattura.getNumero() + ".txt");
        fileFattura.createNewFile();
        Files.writeString(
                Path.of(fileFattura.toURI()),
                fattura.toString(),
                StandardOpenOption.WRITE
        );
    }

    @SneakyThrows
    private void incrementaNumeroProgressivo() {
        this.currentNumber++;
        Files.writeString(
                Path.of(this.datiNumeri.toURI()),
                Calendar.getInstance().get(Calendar.YEAR) + "\n" + (currentNumber),
                StandardOpenOption.WRITE
        );
    }

    @SneakyThrows
    private void inizializzaStorage() {
        Files.writeString(
                Path.of(this.datiNumeri.toURI()),
                Calendar.getInstance().get(Calendar.YEAR) + "\n" + "1",
                StandardOpenOption.WRITE
        );
    }
}
