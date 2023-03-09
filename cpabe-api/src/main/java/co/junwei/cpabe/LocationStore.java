package co.junwei.cpabe;

import co.junwei.bswabe.Bswabe;
import co.junwei.bswabe.BswabePub;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LocationStore {
    private final Map<String, Location> locations;

    private static final LocationStore store = new LocationStore();

    private LocationStore() {
        locations = new HashMap<>();
    }

    public static void addLocation(Location l) {
        store.locations.put(l.locationName, l);
        System.out.println("Putting " + l.locationName + " -> " + l.s_k_x);
    }

    public static Location getLocation(String locationName) {
        Location l = store.locations.get(locationName);
        System.out.println("Getting " + l.locationName + " -> " + l.s_k_x);
        return l;
    }

    public static boolean contains(String locationName) {
        return store.locations.containsKey(locationName);
    }

    public static Element createToken(TrapDoor trapDoor, Pairing pairing, String userLocationFormatDesc) {
        Element h1 = trapDoor.Ax.getField().newElement();

        try {
            Bswabe.elementFromString(h1, userLocationFormatDesc);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        Element e1 = h1.powZn(trapDoor.l.gamma_k);
        Element e2 = trapDoor.Ax;

        Element e = pairing.pairing(e1, e2);
        Element h2 = trapDoor.Bx.getField().newElement();

        try {
            Bswabe.elementFromElement(e, h2);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }

        Element res = trapDoor.Bx.sub(h2);

        Location actualLoc = getLocation(trapDoor.l.locationName);

        System.out.println("======");
        System.out.println(actualLoc.formatDescription);
        System.out.println(actualLoc.s_k_x);
        System.out.println(userLocationFormatDesc);
        System.out.println(res);
        System.out.println("======");

        if (res.equals(actualLoc.s_k_x)) {
            System.out.println("Token retrieved successfully!");
        } else {
            System.out.println("[ERROR] Token does not match!");
        }

        trapDoor.setToken(res);

        return res;
    }
}
