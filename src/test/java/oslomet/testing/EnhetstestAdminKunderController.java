package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKunderController {

    @Mock
    // denne skal Mock'es
    private AdminRepository adminRepository;

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void slettKunde_IkkeLoggetInn() {

        String personNummer = "105010123456";

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.slett(personNummer);

        assertEquals("Ikke logget inn", resultat);

    }

    @Test
    public void slettKunde_LoggetInn() {

        String personNummer = "105010123456";
        String slettetKunde = "";

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(adminRepository.slettKunde(anyString())).thenReturn(slettetKunde);

        String resultat = adminKundeController.slett(personNummer);

        assertEquals(resultat, slettetKunde);
    }

    @Test
    public void endreKunde_IkkeLoggetInn() {

        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.endre(enKunde);

        assertEquals("Ikke logget inn", resultat);

    }

    @Test
    public void endreKunde_LoggetInn() {

        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        String endretKunde = "";

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(adminRepository.endreKundeInfo(enKunde)).thenReturn(endretKunde);

        String resultat = adminKundeController.endre(enKunde);

        assertEquals(resultat, endretKunde);
    }

    @Test
    public void lagreKunde_IkkeLoggetInn() {

        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = adminKundeController.lagreKunde(enKunde);

        assertEquals("Ikke logget inn", resultat);

    }

    @Test
    public void lagreKunde_LoggetInn() {

        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        String endretKunde = "";

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(adminRepository.registrerKunde(enKunde)).thenReturn(endretKunde);

        String resultat = adminKundeController.lagreKunde(enKunde);

        assertEquals(resultat, endretKunde);
    }

    @Test
    public void hentKunder_IkkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);

    }

    @Test
    public void hentKunder_LoggetInn() {

        List<Kunde> kunder = new ArrayList<>();
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde kunde2 = new Kunde("12345678901",
                "Per", "Hansen", "Osloveien 82", "1234",
                "Oslo", "12345678", "HeiHei");

        kunder.add(kunde1);
        kunder.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(adminRepository.hentAlleKunder()).thenReturn(kunder);

        List<Kunde> resultat = adminKundeController.hentAlle();

        assertEquals(resultat, kunder);
    }
}
