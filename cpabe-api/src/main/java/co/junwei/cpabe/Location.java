package co.junwei.cpabe;

import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.DefaultCurveParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.ByteArrayInputStream;

public class Location {
    public String locationName;

    public String formatDescription;

    public Element l_k;

    public Element gamma_k;

    public Element s_k_x;

    public Element v_k;

    public Pairing pairing;

    /**
     * Copy Constructor. Copies every field except the secret (s_k_x).
     */
    public Location(Location l) {
        this.locationName = l.locationName;
        this.formatDescription = l.formatDescription;
        this.l_k = l.l_k;
        this.gamma_k = l.gamma_k;
        this.pairing = l.pairing;
        this.v_k = l.v_k;
        this.s_k_x = l.s_k_x;
    }

    public Location(String locationName,
                    String formatDescription,
                    Pairing p) {
        this.locationName = locationName;
        this.formatDescription = formatDescription;
        this.pairing = p;

        setup();
    }

    private void setup() {
        Pairing p = this.pairing;

        gamma_k = p.getZr().newElement();
        gamma_k.setToRandom();

        l_k = ElementsStore.getG();
        l_k = l_k.powZn(gamma_k);

        v_k = pairing.getZr().newElement();
        v_k.setToRandom();

        s_k_x = pairing.getZr().newElement();
        s_k_x.setToRandom();
//        setSecret();
    }

    public void setSecret() {
        System.out.println("SetSecret called for %s!".formatted(locationName));
//        s_k_x = pairing.getZr().newElement();
//        s_k_x.setToRandom();
        System.out.println("s_k_x: " + s_k_x);
    }

    public static void main(String[] args) {
        String curveParams = "type a\n"
                + "q 87807107996633125224377819847540498158068831994142082"
                + "1102865339926647563088022295707862517942266222142315585"
                + "8769582317459277713367317481324925129998224791\n"
                + "h 12016012264891146079388821366740534204802954401251311"
                + "822919615131047207289359704531102844802183906537786776\n"
                + "r 730750818665451621361119245571504901405976559617\n"
                + "exp2 159\n" + "exp1 107\n" + "sign1 1\n" + "sign0 1\n";
        CurveParameters params = new DefaultCurveParameters()
                .load(new ByteArrayInputStream(curveParams.getBytes()));
        Pairing p = PairingFactory.getPairing(params);

        Location loc = new Location("abc", "12.12,23.23", p);
        loc.setup();
        System.out.println(loc.locationName + ", " + loc.formatDescription + ", [" + loc.gamma_k + "], " + loc.l_k);
    }
}
