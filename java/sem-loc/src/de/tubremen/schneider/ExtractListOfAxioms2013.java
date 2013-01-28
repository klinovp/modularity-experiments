package de.tubremen.schneider;
import java.io.*;
import java.util.TreeMap;

/**
 * @author Thomas Schneider
 */

public class ExtractListOfAxioms2013 {
    static final String pathToData = "/home/pavel/misc/sem-loc/";

//    static final String ontologyFileName = pathToData + "Genuine-All/Onto/adverse-event-reporting-ontology.1580.onto.reduced.txt";
//    static final String moduleFileName = ontologyFileName.substring(0, ontologyFileName.length()-3) + "diff.142.BOT.txt";
//    static final int[] axiomNumbers = {59, 83, 128, 155, 166, 212, 275, 274, 276, 329, 370, 391, 388, 439, 464, 502, 557, 539, 635, 596, 606, 688, 654, 743, 823, 828, 788, 855, 838};

    static final String ontologyFileName = pathToData + "Genuine-Mex-All/Onto/AF_galen.onto.txt";
    static final String moduleFileName = ontologyFileName.substring(0, ontologyFileName.length()-3) + "diff-axioms.txt";
    static final int[] axiomNumbers = {1641, 3, 819, 8, 282, 2196, 566, 1118, 21, 800, 20, 1352, 561, 1661, 29, 809, 1345, 2234, 306, 1131, 2236, 798, 2466, 1877, 318, 1389, 1627, 1865, 1868, 1145, 298, 781, 299, 1862, 1378, 881, 2506, 1576, 2266, 1298, 2263, 2258, 625, 1594, 871, 1054, 2521, 1831, 1066, 1544, 2299, 97, 1342, 381, 2293, 862, 1083, 1565, 1802, 2552, 1800, 599, 1557, 1559, 2615, 958, 1766, 954, 2038, 1502, 2044, 1774, 2596, 2055, 932, 2333, 1788, 2031, 2341, 173, 921, 2345, 1262, 433, 2588, 644, 2347, 438, 2352, 2086, 2561, 2361, 2088, 1755, 1999, 896, 2093, 2133, 744, 202, 2682, 737, 1976, 195, 1958, 1408, 1414, 993, 2665, 448, 2123, 2640, 1670, 2400, 235, 991, 506, 2646, 982, 980, 729, 1203, 1200, 2157, 724, 1928};

    static TreeMap<Integer, String> getAxioms(String ontologyFileName) throws IOException {
        TreeMap<Integer, String> axioms = new TreeMap<Integer, String>();
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(ontologyFileName)));

        while (file.ready()) {
            String line = file.readLine();
            if ((line.length() > 0) && line.matches("[\\d]+:.+")) {
                int colonPos = line.indexOf(":");
                int number = new Integer(line.substring(0, colonPos));
                String contents = line.substring(colonPos+1, line.length());
                axioms.put(number, contents);
            }
        }

        file.close();

        return axioms;
    }

    private static void outputAxioms(int[] axiomNumbers, TreeMap<Integer, String> axioms, String moduleFileName) throws FileNotFoundException {
        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(moduleFileName)));

        for (int number : axiomNumbers) {
            out.print(number + ": ");
            out.println(axioms.get(number));
        }

        out.close();
    }

    public static void main(String[] args) {
        try {
            TreeMap<Integer, String> axioms = getAxioms(ontologyFileName);
            outputAxioms(axiomNumbers, axioms, moduleFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
