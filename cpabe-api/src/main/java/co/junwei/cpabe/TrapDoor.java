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

        Ax = ElementsStore.getG();
        System.out.println("[Trapdoor ctor] g: " + Ax);
        Ax = Ax.powZn(l.v_k);

        Bx = l.s_k_x.duplicate();
        Element tmp0 = p.getG1().newElement();
        // tmp0 = H1(f_loc_k)
        elementFromString(tmp0, l.formatDescription);
        System.out.println("element of format description: " + tmp0);
        Element tmp1 = p.pairing(tmp0, l.l_k);
        // tmp1 = e(H1(f_loc_k), l_k)^{v_k}
        tmp1 = tmp1.powZn(l.v_k);
        System.out.println("before h2: " + tmp1);
        Element tmp2 = p.getZr().newElement();
        // tmp2 = H2(e(H1(f_loc_k, l_k))^{v_k})
        elementFromElement(tmp1, tmp2);
        System.out.println("[Trapdoor ctor] tmp2: " + tmp2);
        System.out.println("[Trapdoor ctor] location: " + l.locationName);
        System.out.println("[Trapdoor ctor] s_k_x: " + Bx);

        Bx = Bx.add(tmp2);

        System.out.println("[Trapdoor ctor] Ax: " + Ax);
        System.out.println("[Trapdoor ctor] Bx: " + Bx);

        Element tmp = tmp0.duplicate();
        tmp.powZn(l.gamma_k);
        System.out.println("[trapdoor ctor] h1^gamma_k: " + tmp);
        System.out.println("[trapdoor ctor] Ax: " + Ax);
        Element eTmp = p.pairing(tmp, Ax);
        System.out.println("[trapdoor ctor] e(...): " + eTmp);
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
