package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @Mock
    // denne skal Mock'es
    private AdminRepository adminRepository;

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void slettKonto_IkkeLoggetInn() {

        String kontonummer = "12345678901";

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKontoController.slettKonto(kontonummer);

        assertEquals("Ikke innlogget", resultat);

    }

    @Test
    public void slettKonto_LoggetInn() {

        String kontonummer = "12345678901";
        String slettetKonto = "";

        when(sjekk.loggetInn()).thenReturn("12345678901");

        when(adminRepository.slettKonto(anyString())).thenReturn(slettetKonto);

        String resultat = adminKontoController.slettKonto(kontonummer);

        assertEquals(slettetKonto, resultat);

    }

    @Test
    public void endreKonto_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);


        String resultat = adminKontoController.endreKonto(new Konto());

        assertEquals("Ikke innlogget", resultat);

    }

    @Test
    public void endreKonto_LoggetInn() {
        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");
        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345", 400.4, "2015-03-20", "Skagen", "1", "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto = new Konto("105010123456", "0101011052", 720, "Lønnskonto", "NOK", transaksjoner);


        when(sjekk.loggetInn()).thenReturn("12345678901");

        when(adminRepository.endreKonto(konto)).thenReturn("OK");

        String resultat = adminKontoController.endreKonto(konto);

        assertEquals("OK", resultat);

    }

    @Test
    public void registrerKonto_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKontoController.registrerKonto(new Konto());

        assertEquals("Ikke innlogget", resultat);

    }

    @Test
    public void registrerKonto_LoggetInn() {

        Konto enKonto = new Konto("12345678901", "0101011052", 720, "Lønnskonto", "NOK", null);

        String endretKonto = "";

        when(sjekk.loggetInn()).thenReturn("12345678901");

        when(adminRepository.registrerKonto(enKonto)).thenReturn(endretKonto);

        String resultat = adminKontoController.registrerKonto(enKonto);

        assertEquals(resultat, endretKonto);
    }

    @Test
    public void hentAlleKonti_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);

    }

    @Test
    public void hentAlleKonti_LoggetInn() {

        when(sjekk.loggetInn()).thenReturn("12345678901");

        List<Konto> konti = adminRepository.hentAlleKonti();

        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertEquals(konti, resultat);

    }
}
