package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;


    @Mock
    // denne skal Mock'es
    private BankRepository repository;


    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {

        String kontoNr = "105010123456";
        String fraDato = "2015-03-15";
        String tilDato = "2015-03-20";

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Konto resultat = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);

        // assert
        assertNull(resultat);

    }

    @Test
    public void hentTransaksjoner_LoggetInn() {

        String kontoNr = "105010123456";
        String fraDato = "2015-03-15";
        String tilDato = "2015-03-20";
        Konto konto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.hentTransaksjoner(kontoNr, fraDato, tilDato)).thenReturn(konto);

        Konto resultat = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);

        assertEquals(konto, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn() {

        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn() {
        Transaksjon transaksjon = new Transaksjon(1, "105010123456", 100,
                "2015-03-15", "12345678901", "Betaling", "OK");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.registrerBetaling(transaksjon);

        assertNull(resultat);

    }

    @Test
    public void registrerBetaling_LoggetInn(){
        Transaksjon transaksjon = new Transaksjon(1, "105010123456", 100,
                "2015-03-15", "12345678901", "Betaling", "OK");

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerBetaling(transaksjon)).thenReturn("OK");

        String resultat = bankController.registrerBetaling(transaksjon);

        assertEquals("OK", resultat);

    }

    @Test
    public void hentBetalinger_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_LoggetInn(){

        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(3, "20102012345", -1400.7, "2015-03-13", "Innbetaling", "1", "55551166677");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void utforBetaling_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_LoggetInn() {
        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345", 100.5, "2015-03-15", "Fjordkraft", "1", "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(3, "20102012345", -1400.7, "2015-03-13", "Innbetaling", "1", "55551166677");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.utforBetaling(1)).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List<Transaksjon> resultat = bankController.utforBetaling(1);

        assertEquals(transaksjoner, resultat);

    }

    @Test
    public void endreKundeInfo_IkkeLoggetInn() {
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.endre(enKunde);

        // assert
        assertNull(resultat);
    }

    @Test
    public void endreKundeInfo_LoggetInn() {
        Kunde enKunde = new Kunde(null,
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.endreKundeInfo(enKunde)).thenReturn("OK");

        String resultat = bankController.endre(enKunde);

        assertEquals("OK", resultat);
    }
}

