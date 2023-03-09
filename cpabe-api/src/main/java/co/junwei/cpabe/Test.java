package co.junwei.cpabe;

import co.junwei.bswabe.Bswabe;
import co.junwei.bswabe.BswabePolicy;
import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.DefaultCurveParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.ByteArrayInputStream;
import java.util.Map;

public class Test {
    private static String curveParams = "type a\n"
            + "q 87807107996633125224377819847540498158068831994142082"
            + "1102865339926647563088022295707862517942266222142315585"
            + "8769582317459277713367317481324925129998224791\n"
            + "h 12016012264891146079388821366740534204802954401251311"
            + "822919615131047207289359704531102844802183906537786776\n"
            + "r 730750818665451621361119245571504901405976559617\n"
            + "exp2 159\n" + "exp1 107\n" + "sign1 1\n" + "sign0 1\n";

    public static void main(String[] args) {
        try {
            CurveParameters params = new DefaultCurveParameters()
                    .load(new ByteArrayInputStream(curveParams.getBytes()));

            String pairingDesc = curveParams;
            Pairing p = PairingFactory.getPairing(params);

            Map<String, Location> locations = Map.of(
                    "l1", new Location("bbs", "10,20", p),
                    "l2", new Location("hyd", "30,40", p)
            );

//            locations.get("l1").setup();
//            locations.get("l2").setup();

            LocationStore.addLocation(locations.get("l1"));
            LocationStore.addLocation(locations.get("l2"));


            BswabePolicy root = Bswabe.testParse("foo bar!bbs 1of2!hyd");
            printPostOrder(root);
            //

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void printPostOrder(BswabePolicy root) {

        String locationString = "";
        if (root.trapDoor != null && root.trapDoor.l != null) {
            locationString = "!" + root.trapDoor.l.locationName;
        }

        BswabePolicy[] children = root.children;
        if (children != null && children.length > 0) {
            for (BswabePolicy child: children) {
                printPostOrder(child);
            }
            System.out.print(root.k + "of" + children.length + locationString + " ");
        } else {
            System.out.print(root.attr + locationString + " ");
        }
    }
}
