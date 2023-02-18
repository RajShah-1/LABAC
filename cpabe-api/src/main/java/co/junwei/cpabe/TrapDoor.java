package co.junwei.cpabe;

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

    public TrapDoor(Location l) throws NoSuchAlgorithmException {
        this.l = l;

        Pairing p = l.pairing;

        Ax = p.getG1().newElement();
        Ax = Ax.powZn(l.v_k);

        Bx = l.s_k_x;
        Element tmp0 = p.getG1().newElement();
        // tmp0 = H1(f_loc_k)
        elementFromString(tmp0, l.formatDescription);
        Element tmp1 = p.pairing(tmp0, l.l_k);
        // tmp1 = e(H1(f_loc_k, l_k))^{v_k}
        tmp1.powZn(l.v_k);
        Element tmp2 = p.getZr().newElement();
        // tmp2 = H2(e(H1(f_loc_k, l_k))^{v_k})
        elementFromElement(tmp2, tmp1);
        Bx.add(tmp2);
    }

    public void serialize(ArrayList<Byte> bytes) {
        serializeString(bytes, l.locationName);
        serializeElement(bytes, this.Ax);
        serializeElement(bytes, this.Bx);
    }
}
