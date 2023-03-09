package co.junwei.cpabe;

import co.junwei.bswabe.Bswabe;
import co.junwei.bswabe.BswabePub;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static co.junwei.bswabe.Bswabe.elementFromElement;
import static co.junwei.bswabe.Bswabe.elementFromString;
import static co.junwei.bswabe.SerializeUtils.*;

public class TrapDoor {
    public Element Ax;
    public Element Bx;

    public Location l;

    public Element token;

    public TrapDoor(Location l, BswabePub pub) throws NoSuchAlgorithmException {
        this.l = l;

        // System.out.println(pub.g);
        Pairing p = l.pairing;

        Ax = pub.g.duplicate();
        Ax = Ax.powZn(l.v_k);

        Bx = l.s_k_x.duplicate();
        Element tmp0 = p.getG1().newElement();
        // tmp0 = H1(f_loc_k)
        elementFromString(tmp0, l.formatDescription);
        Element tmp1 = p.pairing(tmp0, l.l_k);
        // tmp1 = e(H1(f_loc_k), l_k)^{v_k}
        tmp1.powZn(l.v_k);
        Element tmp2 = p.getZr().newElement();
        // tmp2 = H2(e(H1(f_loc_k, l_k))^{v_k})
        elementFromElement(tmp1, tmp2);
        System.out.println("tmp2" + tmp2);
        System.out.println("s_k_x" + Bx);
        Bx.add(tmp2);
        System.out.println("Ax: " + Ax);
        System.out.println("Bx: " + Bx);
    }

    public void serialize(ArrayList<Byte> bytes) {
        serializeString(bytes, l.locationName);
        serializeElement(bytes, this.Ax);
        serializeElement(bytes, this.Bx);
    }

    public void setToken(Element token) {
        this.token = token;
    }

    public Element exposeTrapdoor(Pairing pairing, Element d_prime) {
        return pairing.pairing(this.token, d_prime);
    }
}
