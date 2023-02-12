package co.junwei.cpabe;

import co.junwei.bswabe.Bswabe;
import co.junwei.bswabe.BswabePolicy;

import java.util.Map;

public class Test {
    public static void main(String[] args) {
        try {
            Map<String, Location> locations = Map.of(
                    "l1", new Location("bbs", "10,20"),
                    "l2", new Location("hyd", "30,40")
            );
            BswabePolicy root = Bswabe.testParse("foo bar!l1 1of2!l2", locations);
            printPostOrder(root);
            //

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void printPostOrder(BswabePolicy root) {
        String locationString = "";
        if (root.l1 != null) {
            locationString = "!" + root.l1.locationName;
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
