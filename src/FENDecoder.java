import java.util.Map;

public class FENDecoder {
   static Map<Character,Integer> defaultDecodeMap=Map.ofEntries(
        Map.entry('K',1),
        Map.entry('Q',2),
        Map.entry('B',3),
        Map.entry('N',4),
        Map.entry('R',5),
        Map.entry('P',6),

        Map.entry('k',8 | 1),
        Map.entry('q',8 | 2),
        Map.entry('b',8 | 3),
        Map.entry('n',8 | 4),
        Map.entry('r',8 | 5),
        Map.entry('p',8 | 6),

        Map.entry('#', 0)
    );

    public static int[][] fenToArray(String fenString) {
        return fenToArray(fenString, defaultDecodeMap);
    }

    public static int[][] fenToArray(String fenString,Map<Character,Integer> decodeMap) {
        String[] parts=fenString.split(" ");
        String[] positionParts=parts[0].split("/");

        int[][] result=new int[8][8];


        char[] pieces=new char[64];

        int count=0;

        for (int i=0;i<positionParts.length;i++) {
            for (int j=0;j<positionParts[i].length();j++) {
                char currentChar=positionParts[i].charAt(j);
                if (Character.isDigit(currentChar)) {
                    for (int k=0;k<Integer.parseInt(String.valueOf(currentChar));k++) {
                        pieces[count]='#';
                        count++;
                    }
                } else {
                    pieces[count]=positionParts[i].charAt(j);
                    count++;
                }
            }
        }

        count=0;
        for (int i=0;i<8;i++) {
            for (int k=0;k<8;k++) {
                result[i][k]=decodeMap.get(pieces[count]);
                count++;
            }
        }

        return result;
    }
}
